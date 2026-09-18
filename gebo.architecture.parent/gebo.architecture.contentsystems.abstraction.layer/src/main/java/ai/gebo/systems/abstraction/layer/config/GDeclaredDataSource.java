/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer.config;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * One data source declared under {@code ai.gebo.<content handler>.datasources}:
 * a project endpoint the deployment owns, pointed at a system it also declares
 * (or at one an admin created), over an explicit set of paths.
 *
 * <pre>
 * ai.gebo.webdav:
 *   datasources:
 *     - code: corporate-policies
 *       description: Policy library
 *       systemCode: corporate-dav
 *       parentProjectCode: COMPANY-KB
 *       paths:
 *         - path: https://dav.example.com/remote.php/dav/files/admin/Policies
 *           folder: true
 *         - path: https://dav.example.com/remote.php/dav/files/admin/handbook.pdf
 *           folder: false
 * </pre>
 *
 * <h2>Why this is one shared type rather than one per handler</h2>
 * <p>
 * Every endpoint this covers extends {@code GVirtualFilesystemProjectEndpoint},
 * so what a declaration has to say is the same in all four cases: which system,
 * under which project, over which paths. What differs is only the module's own
 * field for the system code ({@code webdavSystemCode}, {@code s3SystemCode},
 * ...) and the encoding of a path - and both of those are the concrete DAO's
 * job, not the declaration's. Binding the concrete endpoint class directly, the
 * way the systems lists do, would instead have forced the deployment to write
 * the module's internal {@code VFilesystemReference} encoding by hand.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Data
public class GDeclaredDataSource {

	/** The code this data source is resolved by. Required, unique per handler. */
	@NotBlank
	private String code = null;

	/** Human-readable description, as shown by the admin surface. */
	private String description = null;

	/**
	 * The code of the content management system this source reads through -
	 * declared under {@code ai.gebo.<content handler>.systems} or created in the
	 * admin UI. Required.
	 */
	@NotBlank
	private String systemCode = null;

	/**
	 * The knowledge base project this source feeds. Required in practice for the
	 * source to be reachable from a project, though a deployment may declare it
	 * later.
	 */
	private String parentProjectCode = null;

	/** Whether the source is published to the knowledge base. */
	private Boolean published = true;

	/**
	 * Whether the source is re-synchronized on the periodic schedule. A marker
	 * only: what actually puts a source on the scheduler is
	 * {@link #programmedTables}.
	 */
	private Boolean synchPeriodically = null;

	/**
	 * When the source is re-ingested, which is what the central scheduler reads.
	 *
	 * <pre>
	 * programmedTables:
	 *   - frequency: DAILY
	 *     times:
	 *       - timeComponent: [2, 30]
	 * </pre>
	 *
	 * <p>
	 * The shape of {@code timeComponent} follows the frequency, as
	 * {@code ReindexTimeStructureMetaInfo} defines it: {@code [minutes]} for
	 * {@code HOURLY}, {@code [hour, minutes]} for {@code DAILY},
	 * {@code [dayOfWeek, hour, minutes]} for {@code WEEKLY},
	 * {@code [weekOfMonth, dayOfWeek, hour, minutes]} for {@code MONTHLY}.
	 * </p>
	 *
	 * <p>
	 * A schedule alone does not start anything: the scheduler is driven by
	 * reschedule requests that only a write path emits, so a declared source joins
	 * the schedule when it is published once - see the publishing section of the
	 * configuration guide.
	 * </p>
	 */
	private List<@Valid ReindexingProgrammedTable> programmedTables = null;

	/** Whether archives found in the source are opened and walked. */
	private Boolean openZips = null;

	/**
	 * Whether this source is known to hold personal data (GDPR); the compliance
	 * data-flow register reads it from the endpoint.
	 */
	private Boolean personalData = null;

	/** Only these extensions are vectorized, when set. */
	private List<String> vectorizeOnlyExtensions = null;

	/**
	 * The paths to ingest. At least one: a data source with no path would connect
	 * and read nothing.
	 */
	@NotEmpty
	private List<@Valid GDeclaredDataSourcePath> paths = new ArrayList<GDeclaredDataSourcePath>();
}
