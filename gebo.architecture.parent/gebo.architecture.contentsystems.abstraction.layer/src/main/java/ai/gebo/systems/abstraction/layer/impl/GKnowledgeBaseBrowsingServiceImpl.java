/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.IGKnowledgeBaseBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.KnowledgeBaseBrowsingServiceSelectedReferences;
import ai.gebo.systems.abstraction.layer.model.KnowledgeBaseContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Service implementation for browsing knowledge bases within a virtual filesystem context.
 * AI generated comments
 */
@Service
public class GKnowledgeBaseBrowsingServiceImpl implements IGKnowledgeBaseBrowsingService {
    private static final String KNOWLEDGEBASE_PREFIX = "knowledgebase:";
    private static final String PROJECT_PREFIX = "project:";
    private static final String ENDPOINT_PREFIX = "endpoint:";
    private static final String VIRTUAL_FOLDER_PREFIX = "vfolder:";
    private static final String DOCUMENT_PREFIX = "document:";

    // Repositories and managers for accessing data layer
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    IGPersistentObjectManager persistenceManager;
    @Autowired
    VirtualFolderRepository vFolderRepository;
    @Autowired
    DocumentReferenceRepository docReferences;

    /**
     * Default constructor.
     */
    public GKnowledgeBaseBrowsingServiceImpl() {

    }

    /**
     * Converts a knowledge base to a virtual filesystem root.
     */
    private static Function<GKnowledgeBase, GVirtualFilesystemRoot> knowledgeBase2Root = x -> {
        GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
        root.setCode(KNOWLEDGEBASE_PREFIX + x.getCode());
        root.setDescription(x.getDescription());
        root.setAbsolutePath(KNOWLEDGEBASE_PREFIX + root.getCode());
        return root;
    };

    /**
     * Gets the root directories for the given knowledge base context.
     *
     * @param context The context containing knowledge bases.
     * @return OperationStatus containing a list of virtual filesystem roots.
     * @throws VirtualFilesystemBrowsingException if any issue occurs during the operation.
     */
    @Override
    public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(KnowledgeBaseContext context)
            throws VirtualFilesystemBrowsingException {
        List<GKnowledgeBase> knowledgebases = context.getKnowledgeBases();

        return OperationStatus.of(knowledgebases.stream().map(knowledgeBase2Root).toList());
    }

    /**
     * Browses the virtual filesystem based on the given parameters.
     *
     * @param param   The browse parameters.
     * @param context The context of the knowledge base.
     * @return OperationStatus containing a list of path information.
     * @throws VirtualFilesystemBrowsingException if any issue occurs during the operation.
     */
    @Override
    public OperationStatus<List<PathInfo>> browse(BrowseParam param, KnowledgeBaseContext context)
            throws VirtualFilesystemBrowsingException {
        if (param.root != null) {
            if (param.root.getCode().startsWith(KNOWLEDGEBASE_PREFIX)) {
                String kbCode = param.root.getCode().substring(KNOWLEDGEBASE_PREFIX.length());
                if (param.path != null) {
                    if (param.path.absolutePath.startsWith(PROJECT_PREFIX)) {
                        String pjCodeInPath = param.path.absolutePath.substring(PROJECT_PREFIX.length());
                        return browseProject(pjCodeInPath);
                    } else if (param.path.absolutePath.startsWith(ENDPOINT_PREFIX)) {
                        String endpointCompositCode = param.path.absolutePath.substring(ENDPOINT_PREFIX.length());
                        return browseEndpoint(endpointCompositCode);
                    } else if (param.path.absolutePath.startsWith(VIRTUAL_FOLDER_PREFIX)) {
                        String vfolderCode = param.path.absolutePath.substring(VIRTUAL_FOLDER_PREFIX.length());
                        return browseVFolder(vfolderCode);
                    } else {
                        throw new RuntimeException("Browsing node:" + param.path.absolutePath + " of unknown type");
                    }
                } else {
                    return browseKnowledgeBase(kbCode);
                }
            } else {
                throw new RuntimeException("Root only elements must have a project: prefix");
            }
        } else {
            throw new RuntimeException("Browsing param without root is not accepted");
        }
    }

    /**
     * Browse the knowledge base and return a list of path information for projects.
     *
     * @param kbCode The code of the knowledge base.
     * @return OperationStatus containing a list of path information.
     */
    private OperationStatus<List<PathInfo>> browseKnowledgeBase(String kbCode) {
        return OperationStatus.of(projectRepository.findByRootKnowledgeBaseCode(kbCode).map(x -> {
            PathInfo pinfo = new PathInfo();
            pinfo.absolutePath = PROJECT_PREFIX + x.getCode();
            pinfo.name = x.getDescription();
            pinfo.folder = true;
            return pinfo;
        }).toList());
    }

    /**
     * Converts a project into a PathInfo object.
     */
    private static Function<GProject, PathInfo> project2path = x -> {
        PathInfo pinfo = new PathInfo();
        pinfo.absolutePath = PROJECT_PREFIX + x.getCode();
        pinfo.name = x.getDescription();
        pinfo.folder = true;
        return pinfo;
    };

    /**
     * Converts a project endpoint into a PathInfo object.
     */
    private static Function<GProjectEndpoint, PathInfo> gprojectEndpoint2path = x -> {
        PathInfo pinfo = new PathInfo();
        pinfo.absolutePath = ENDPOINT_PREFIX + x.getCode() + ":" + x.getClass().getName();
        pinfo.name = x.getDescription();
        pinfo.folder = true;
        return pinfo;
    };

    /**
     * Browse the project identified by the given code and return its sub-projects and endpoints.
     *
     * @param pjCode The code of the project to browse.
     * @return OperationStatus containing a list of path information.
     * @throws VirtualFilesystemBrowsingException if any issue occurs during the operation.
     */
    private OperationStatus<List<PathInfo>> browseProject(String pjCode) throws VirtualFilesystemBrowsingException {
        try {
            Stream<GProject> pjstream = projectRepository.findByParentProjectCode(pjCode);
            List<PathInfo> subProjects = pjstream.map(project2path).toList();
            List<GProjectEndpoint> endpoints;

            endpoints = persistenceManager.findAllByQbeSettingFunction(GProjectEndpoint.class, (x) -> {
                x.setParentProjectCode(pjCode);
            });

            List<PathInfo> subEndpoints = endpoints.stream().map(gprojectEndpoint2path).toList();
            List<PathInfo> out = new ArrayList<PathInfo>();
            out.addAll(subProjects);
            out.addAll(subEndpoints);
            return OperationStatus.of(out);
        } catch (GeboPersistenceException e) {
            throw new VirtualFilesystemBrowsingException("Exception in browseProject(..)", e);
        }
    }

    /**
     * Converts a virtual filesystem object into a PathInfo object.
     */
    private static Function<GAbstractVirtualFilesystemObject, PathInfo> virtualFilesystemObject2path = (
            GAbstractVirtualFilesystemObject x) -> {
        PathInfo pinfo = new PathInfo();
        pinfo.folder = x instanceof GVirtualFolder;
        pinfo.absolutePath = (pinfo.folder ? VIRTUAL_FOLDER_PREFIX : DOCUMENT_PREFIX) + x.getCode();
        pinfo.name = x.getName();
        return pinfo;
    };

    /**
     * Browse the virtual folder identified by the given code.
     *
     * @param vfolderCode The code of the virtual folder to browse.
     * @return OperationStatus containing a list of path information.
     */
    private OperationStatus<List<PathInfo>> browseVFolder(String vfolderCode) {
        Stream<? extends GAbstractVirtualFilesystemObject> folderStream = vFolderRepository
                .findByParentVirtualFolderCode(vfolderCode);
        Stream<? extends GAbstractVirtualFilesystemObject> filestream = docReferences
                .findByParentVirtualFolderCode(vfolderCode);
        Stream<PathInfo> catenation = Stream.concat(folderStream, filestream)
                .filter(x -> x.getDeleted() == null || !x.getDeleted()).map(virtualFilesystemObject2path);
        return OperationStatus.of(catenation.toList());
    }

    /**
     * Browse the project endpoint identified by the given composite code.
     *
     * @param endpointCompositCode The composite code of the endpoint to browse.
     * @return OperationStatus containing a list of path information.
     */
    private OperationStatus<List<PathInfo>> browseEndpoint(String endpointCompositCode) {
        StringTokenizer tokenizer = new StringTokenizer(endpointCompositCode, ":");
        String code = tokenizer.nextToken();
        String className = tokenizer.nextToken();
        Stream<GVirtualFolder> folders = vFolderRepository
                .findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCodeIsNull(
                        className, code);
        return OperationStatus.of(folders.map(virtualFilesystemObject2path).toList());
    }

    /**
     * Checks if navigation status is supported.
     *
     * @return true if navigation is supported, false otherwise.
     */
    @Override
    public boolean isSupportsNavigationStatus() {
        return true;
    }

    /**
     * TODO: Provide an implementation for getting the parent of a given filesystem reference.
     *
     * @param reference The reference to find the parent for.
     * @param context   The context of the knowledge base.
     * @return VFilesystemReference of the parent.
     * @throws VirtualFilesystemBrowsingException if any issue occurs during the operation.
     */
    @Override
    public VFilesystemReference getParent(VFilesystemReference reference, KnowledgeBaseContext context)
            throws VirtualFilesystemBrowsingException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Retrieves references for the given list of filesystem references.
     *
     * @param references The list of filesystem references.
     * @return KnowledgeBaseBrowsingServiceSelectedReferences containing the references.
     * @throws VirtualFilesystemBrowsingException if any issue occurs during the operation.
     * @throws GeboPersistenceException if any persistence error occurs.
     */
    @Override
    public KnowledgeBaseBrowsingServiceSelectedReferences getReferences(
            @NotNull @Valid List<VFilesystemReference> references)
            throws VirtualFilesystemBrowsingException, GeboPersistenceException {
        final List<GKnowledgeBase> knowledgeBases = new ArrayList<>();
        final List<GProject> projects = new ArrayList<>();
        final List<GProjectEndpoint> endpoints = new ArrayList<>();
        final List<GVirtualFolder> virtualFolders = new ArrayList<>();
        final List<GDocumentReference> documents = new ArrayList<>();
        for (VFilesystemReference reference : references) {
            if (reference.root != null) {
                if (reference.root.getCode().startsWith(KNOWLEDGEBASE_PREFIX)) {
                    String kbCode = reference.root.getCode().substring(KNOWLEDGEBASE_PREFIX.length());
                    if (reference.path != null) {
                        if (reference.path.absolutePath != null) {
                            if (reference.path.absolutePath.startsWith(PROJECT_PREFIX)) {
                                String pjCodeInPath = reference.path.absolutePath.substring(PROJECT_PREFIX.length());
                                GProject project = persistenceManager.findById(GProject.class, pjCodeInPath);
                                if (project != null)
                                    projects.add(project);
                            } else if (reference.path.absolutePath.startsWith(ENDPOINT_PREFIX)) {
                                String endpointCompositCode = reference.path.absolutePath
                                        .substring(ENDPOINT_PREFIX.length());
                                StringTokenizer tokenizer = new StringTokenizer(endpointCompositCode, ":");
                                String code = tokenizer.nextToken();
                                String className = tokenizer.nextToken();
                                GObjectRef<GProjectEndpoint> ref = new GObjectRef<>();
                                ref.setClassName(className);
                                ref.setCode(code);
                                GProjectEndpoint endpoint = persistenceManager.findByReference(ref,
                                        GProjectEndpoint.class);
                                if (endpoint != null)
                                    endpoints.add(endpoint);
                            } else if (reference.path.absolutePath.startsWith(VIRTUAL_FOLDER_PREFIX)) {
                                String vfolderCode = reference.path.absolutePath
                                        .substring(VIRTUAL_FOLDER_PREFIX.length());
                                GVirtualFolder virtualFolder = persistenceManager.findById(GVirtualFolder.class,
                                        vfolderCode);
                                if (virtualFolder != null)
                                    virtualFolders.add(virtualFolder);
                            }
                        } else {
                            throw new VirtualFilesystemBrowsingException(
                                    "vfilesystem reference with null absolutePath");
                        }
                    } else {

                        GKnowledgeBase kb = persistenceManager.findById(GKnowledgeBase.class, kbCode);
                        if (kb != null)
                            knowledgeBases.add(kb);

                    }
                }
            }
        }
        return new KnowledgeBaseBrowsingServiceSelectedReferences(knowledgeBases, projects, endpoints, virtualFolders,
                documents);
    }

    /**
     * TODO: Provide an implementation for getting a list of filesystem reference representations.
     *
     * @param references The selected references.
     * @return List of VFilesystemReferences.
     * @throws VirtualFilesystemBrowsingException if any issue occurs during the operation.
     * @throws GeboPersistenceException if any persistence error occurs.
     */
    @Override
    public List<VFilesystemReference> getVFilesystemReferenceRappresentation(
            @NotNull @Valid KnowledgeBaseBrowsingServiceSelectedReferences references)
            throws VirtualFilesystemBrowsingException, GeboPersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Converts a knowledge base into a filesystem reference.
     *
     * @param kb The knowledge base.
     * @return VFilesystemReference
     */
    @Override
    public VFilesystemReference toReference(GKnowledgeBase kb) {
        VFilesystemReference out = new VFilesystemReference();
        out.root = knowledgeBase2Root.apply(kb);
        return out;
    }

    /**
     * Converts a project into a filesystem reference.
     *
     * @param pj The project.
     * @return VFilesystemReference
     * @throws GeboPersistenceException if any persistence error occurs.
     */
    @Override
    public VFilesystemReference toReference(GProject pj) throws GeboPersistenceException {
        GKnowledgeBase knowledgeBase = persistenceManager.findById(GKnowledgeBase.class, pj.getRootKnowledgeBaseCode());
        VFilesystemReference out = toReference(knowledgeBase);
        out.path = project2path.apply(pj);
        return out;
    }

    /**
     * Converts a project endpoint into a filesystem reference.
     *
     * @param pj The project endpoint.
     * @return VFilesystemReference
     * @throws GeboPersistenceException if any persistence error occurs.
     */
    @Override
    public VFilesystemReference toReference(GProjectEndpoint pj) throws GeboPersistenceException {
        GProject project = persistenceManager.findById(GProject.class, pj.getParentProjectCode());
        VFilesystemReference out = toReference(project);
        out.path = gprojectEndpoint2path.apply(pj);
        return out;
    }

    /**
     * Converts a list of virtual filesystem objects into a collection of filesystem references.
     *
     * @param virtualfsObjects The list of virtual filesystem objects.
     * @return Collection of VFilesystemReferences
     * @throws GeboPersistenceException if any persistence error occurs.
     */
    @Override
    public Collection<VFilesystemReference> toReferences(List<GAbstractVirtualFilesystemObject> virtualfsObjects)
            throws GeboPersistenceException {
        Map<String, GObjectRef<GProjectEndpoint>> maps = new HashMap<>();
        Map<String, List<GAbstractVirtualFilesystemObject>> docsSplit = new HashMap<>();
        for (GAbstractVirtualFilesystemObject obj : virtualfsObjects) {
            if (obj.getProjectEndpointReference() != null) {
                String key = obj.getProjectEndpointReference().key();
                maps.put(key, obj.getProjectEndpointReference());
                if (!docsSplit.containsKey(key)) {
                    docsSplit.put(key, new ArrayList<>());
                }
                docsSplit.get(key).add(obj);
            }
        }
        List<VFilesystemReference> out = new ArrayList<>();
        for (GObjectRef<GProjectEndpoint> ref : maps.values()) {
            GProjectEndpoint endpoint = persistenceManager.findByReference(ref, GProjectEndpoint.class);
            if (endpoint != null) {
                final VFilesystemReference base = toReference(endpoint);
                List<GAbstractVirtualFilesystemObject> objects = docsSplit.get(ref.key());
                if (objects != null) {
                    List<VFilesystemReference> pathinfos = objects.stream().map(virtualFilesystemObject2path).map(x -> {
                        VFilesystemReference data = new VFilesystemReference();
                        data.root = base.root;
                        data.path = x;
                        return data;
                    }).toList();
                    out.addAll(pathinfos);
                }
            }
        }
        return out;
    }

}