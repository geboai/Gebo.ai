/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.maven.buildsystem.handler.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ai.gebo.architecture.buildsystems.abstraction.layer.IGArtifactMetaInfosRenderer;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemConfigurationDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GPackageManager;
import ai.gebo.knlowledgebase.model.contents.GSoftwareArtifact;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GBuildSystemType;
import ai.gebo.maven.buildsystem.handler.IGMavenBuildSystemHandler;
import ai.gebo.maven.buildsystem.handler.config.GBaseMavenBuildSystemConfig;

@Service
public class GAbstractMavenBuildSystemHandlerImpl implements IGMavenBuildSystemHandler {

    /** 
     * AI generated comments
     * Default build system type for Maven. 
     */
    public static final GBuildSystemType defaultSystemType = new GBuildSystemType();

    /** Common ObjectMapper instance with specific configurations */
    private static ObjectMapper mapper = new ObjectMapper();

    // Static block to configure the ObjectMapper
    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // Static block to initialize default build system type with code and description
    static {
        defaultSystemType.setCode("MAVEN.BUILD.SYSTEM");
        defaultSystemType.setDescription("Maven build & packaging system");
    }

    // DAO for build system configuration
    IGBuildSystemConfigurationDao<GBaseMavenBuildSystemConfig> configDao = null;

    // Renderer for artifact metadata information
    IGArtifactMetaInfosRenderer artifactRenderer = null;

    /**
     * Constructor to initialize GAbstractMavenBuildSystemHandlerImpl with required dependencies.
     * 
     * @param configDao The configuration DAO for Maven build system.
     * @param artifactRenderer The renderer for artifact metadata info.
     */
    public GAbstractMavenBuildSystemHandlerImpl(
            @Autowired IGBuildSystemConfigurationDao<GBaseMavenBuildSystemConfig> configDao,
            @Autowired IGArtifactMetaInfosRenderer artifactRenderer) {
        this.configDao = configDao;
        this.artifactRenderer = artifactRenderer;
    }

    @Override
    public GBuildSystemType getHandledSystemType() {
        return defaultSystemType;
    }

    /**
     * Checks if the given build system is applicable for the provided path.
     * 
     * @param system The build system configuration.
     * @param path The file path to check.
     * @return Boolean indicating if the build system can be applied.
     */
    protected boolean isAppliable(GBuildSystem<GBaseMavenBuildSystemConfig> system, File path) {
        // TODO: IMPLEMENT SYSTEM CHOICE
        return true;
    }

    @Override
    public GBuildSystem<GBaseMavenBuildSystemConfig> autoconfigure(File path) throws GeboContentHandlerSystemException {
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.getName().toLowerCase().equals("pom.xml")) {
                        GBuildSystem<GBaseMavenBuildSystemConfig> buildSystem = null;
                        if (configDao != null) {
                            List<GBuildSystem<GBaseMavenBuildSystemConfig>> configs = configDao.getConfigurations();
                            for (GBuildSystem<GBaseMavenBuildSystemConfig> system : configs) {
                                if (isAppliable(system, path)) {
                                    buildSystem = system;
                                }
                            }
                        }
                        return buildSystem;
                    }
                }
            }
            return null;
        } else
            throw new GeboContentHandlerSystemException("The checked path: " + path.getAbsolutePath() + " is not a directory");
    }

    /**
     * Extracts a software artifact's details from the given XML element.
     * 
     * @param item XML element representing an artifact.
     * @param vfolder Virtual folder context for the artifact.
     * @return Extracted software artifact.
     */
    protected GSoftwareArtifact extractElement(Element item, GVirtualFolder vfolder) {
        GSoftwareArtifact deliverable = null;
        NodeList childs = item.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node childitem = childs.item(i);
            if (childitem instanceof Element) {
                Element _elementChild = (Element) childitem;
                if (_elementChild.getTagName().equals("version") && _elementChild.getTextContent() != null) {
                    if (deliverable == null)
                        deliverable = new GSoftwareArtifact();
                    deliverable.setVersion(_elementChild.getTextContent().trim());
                }
                if (_elementChild.getTagName().equals("artifactId") && _elementChild.getTextContent() != null) {
                    if (deliverable == null)
                        deliverable = new GSoftwareArtifact();
                    deliverable.setArtifactId(_elementChild.getTextContent().trim());
                }
                if (_elementChild.getTagName().equals("groupId") && _elementChild.getTextContent() != null) {
                    if (deliverable == null)
                        deliverable = new GSoftwareArtifact();
                    deliverable.setModuleId(_elementChild.getTextContent().trim());
                }
                if (_elementChild.getTagName().equals("packaging") && _elementChild.getTextContent() != null) {
                    if (deliverable == null)
                        deliverable = new GSoftwareArtifact();
                }
            }
        }
        if (deliverable != null && vfolder != null) {
            deliverable.setLanguage("java");
            deliverable.setPackageManager(GPackageManager.maven);
            deliverable.setParentVirtualFolderCode(vfolder.getCode());
            deliverable.setRootKnowledgebaseCode(vfolder.getRootKnowledgebaseCode());
            deliverable.setParentProjectCode(vfolder.getParentProjectCode());
        }
        return deliverable;
    }

    /**
     * Reads a Maven POM file, extracting its dependencies and artifacts into a dependency tree.
     * 
     * @param path The path to the directory containing the POM file.
     * @param vfolder The virtual folder context.
     * @return A dependency tree constructed from the POM file.
     * @throws GeboContentHandlerSystemException Thrown if an error occurs during parsing.
     */
    protected GDependencyTree readPom(Path path, GVirtualFolder vfolder) throws GeboContentHandlerSystemException {
        GDependencyTree outTree = null;
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path _path : stream) {
                if (_path.getFileName().toString().toLowerCase().equals("pom.xml")) {
                    Element dependencies = null, parent = null;
                    try (InputStream is = Files.newInputStream(_path, StandardOpenOption.READ)) {
                        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
                        GSoftwareArtifact mainDeliverable = extractElement(document.getDocumentElement(), vfolder);
                        NodeList childs = document.getDocumentElement().getChildNodes();
                        for (int j = 0; j < childs.getLength(); j++) {
                            Node childitem = childs.item(j);
                            if (childitem instanceof Element) {
                                Element _elementChild = (Element) childitem;
                                if (_elementChild.getNodeName().equals("dependencies")) {
                                    dependencies = _elementChild;
                                }
                                if (_elementChild.getNodeName().equals("parent")) {
                                    parent = _elementChild;
                                }
                            }
                        }
                        if (mainDeliverable != null) {
                            outTree = new GDependencyTree(mainDeliverable);
                            outTree.setDependencies(new ArrayList<GDependencyTree>());
                            if (parent != null) {
                                GSoftwareArtifact parentDeliverable = extractElement(parent, null);
                                if (parentDeliverable != null) {
                                    GDependencyTree _parent = new GDependencyTree(parentDeliverable);
                                    outTree.setParentArtifact(_parent);
                                }
                            }
                            if (dependencies != null) {
                                NodeList dependenciesList = dependencies.getElementsByTagName("dependency");
                                for (int j = 0; j < dependenciesList.getLength(); j++) {
                                    Element child = (Element) dependenciesList.item(j);
                                    GDependencyTree item = new GDependencyTree(extractElement(child, null));
                                    
                                    if (item.getArtifactId() != null) {
                                        outTree.getDependencies().add(item);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (outTree != null) {
                if (outTree.getModuleId() == null) {
                    outTree.setModuleId(outTree.getParentArtifact().getModuleId());
                }
                if (outTree.getVersion() == null) {
                    outTree.setVersion(outTree.getParentArtifact(). getVersion());
                }
            }
            if (outTree != null) {
                outTree.setPackageManager(GPackageManager.maven);
            }
            return outTree;
        } catch (Throwable exc) {
            throw new GeboContentHandlerSystemException("Exception reading pom.xml", exc);
        }
    }

    @Override
    public GBuildSystem<GBaseMavenBuildSystemConfig> findConfigurationByCode(String code) {
        return configDao.findByCode(code);
    }

    @Override
    public void consumeCustomAdditionalMetaInformations(GBuildSystem<GBaseMavenBuildSystemConfig> buildSystemConfig,
            GVirtualFolder item, Path path, IGContentConsumer consumer) throws GeboContentHandlerSystemException {
        // No specific implementation needed.
    }

    @Override
    public List<GDependencyTree> extractDeliverablesMetaInfos(
            GBuildSystem<GBaseMavenBuildSystemConfig> buildSystemConfig, GVirtualFolder item, Path path)
            throws GeboContentHandlerSystemException {
        GDependencyTree tree = readPom(path, item);
        if (tree != null)
            return List.of(tree);
        else
            return new ArrayList<GDependencyTree>();
    }

    @Override
    public List<GBuildSystem<GBaseMavenBuildSystemConfig>> getConfigurations() {
        return this.configDao.getConfigurations();
    }

}