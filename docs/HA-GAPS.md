# High availability — what has to be refactored

A register of the parts of Gebo.ai that were written for one running instance of a service and
have to be refactored before several instances of that service can run as a matter of course.
An entry says where the part is, what happens with several instances and why, read in the code,
what it costs as things are, what the code already has to build on, and what a refactoring would
need. It records findings, not decisions: the design of each refactoring is taken when it is done.

Entries are added as they are found. Each one names the repository, the Maven module and the step
of the workflow it is about.

## 1. Filesystem content handler: following the changes of the files

**Where.** OSS repository, module `gebo.systems.parent/gebo.filesystem.content.handler`, class
`GFilesystemChangesHandlingService`; the stored changes are `GFilesystemChangesQueueEntry`
(Mongo). The step is the automatic refresh of a filesystem data source flagged
`syncOnFilesModifications`, between one publication and the next. In the microservices deployment
the handler is the `filesystem` service, whose Helm chart
(`deploy/helm/gebo-microservices/templates/backends.yaml`) takes a number of `replicas`.

**With one instance.** A pool of threads wakes at every period, reads the followed data sources
from the database, compares each folder with the picture of it the instance keeps in memory,
stores what changed and, when the changes of a data source gathered for the window and the data
source is not being published, hands the whole group to the ingestion in one session.

**With several instances.** Nothing coordinates the instances, so each one does all of the above
on its own:

1. **Every instance looks at every followed data source.** The followed data sources are read
   from the shared database (`FilesystemProjectEndpointRepository.findBySyncOnFilesModificationsTrue`),
   and each instance has its own pictures (`pictures`, a map in memory). The work of scanning is
   done once per instance.
2. **A change is stored once per instance.** Each instance that sees a file changed writes its own
   `GFilesystemChangesQueueEntry` in the same collection.
3. **A group can be handed over twice.** `handOverDue` reads every entry stored for a data source
   (`findByEndpointCodeOrderByDetectedAtAscPartAsc`), whichever instance stored it, with no claim
   on them. One instance handing over the copies of a change tells each file once (`changesOf`
   merges them). Two instances doing it at the same time, or one after the other before the
   entries of the second are stored, open two sessions of the ingestion for the same change.
4. **What a second session costs.** The ingestion compares a document with what was last ingested
   of it (`GContentDispatchingEvaluatorImpl`), so a file handed over again unchanged is expected
   to be discarded once the acknowledgement of the first session is stored; when the two sessions
   run together the file can be chunked and embedded twice. A file gone is told deleted twice:
   its stores are erased twice, to no effect. This was read in the code, not tried with two
   instances.
5. **An instance does not know that another one is publishing.** Whether a data source is being
   published is asked to `GeboIngestionManager.isJobRunning`. Unless `ai.gebo.config.clustered` is
   true it answers from a map in the memory of the instance (`statusMap`), filled while that
   instance reads the data source. So an instance can look at, and hand over the changes of, a
   data source another instance is publishing; the same answer is what refuses a second
   publication of a data source, which is therefore refused only on the instance already
   publishing it. With `ai.gebo.config.clustered=true` the answer comes from the job statuses in
   the database. Neither the Helm chart nor the configuration of the microservices sets it.
6. **The folders must be the same on every instance.** A data source names its folders by path.
   An instance that does not see a folder leaves it as it last saw it and stores nothing (a folder
   that cannot be listed is not read as emptied), so it deletes nothing; it simply does no part of
   the work for that folder.
7. **The pictures do not outlive an instance.** An instance that starts pictures each folder as it
   finds it. What changed while no instance was looking is found by the next publication. This
   holds for one instance too; with several, the instances started at different moments hold
   different pictures of the same folder.

**What it costs as things are.** Scanning and storing are multiplied by the number of instances,
and a changed file can be embedded once more than needed. Nothing is lost and nothing is deleted
by mistake. Following the changes of the files is right with one instance of the handler.

**What the code already has to build on.**

- `SchedulerLeaderLeaseService` and `GSchedulerLeaderLease` (`gebo.architecture.scheduling`): a
  lease in Mongo, taken and renewed with `findAndModify`, that elects one replica of `tyr` to run
  the tick of the central scheduler.
- The stored changes are already in Mongo and already carry the data source, the folder and the
  moment they were found.
- `ai.gebo.config.clustered` and the job statuses in the database already give an answer to "is
  this data source being published" that holds across instances.

**What a refactoring would need.**

- One instance at a time looking at a data source: a lease for the handler, or one for each data
  source so that the instances share the data sources among them.
- A claim on the stored changes before they are handed over (a field saying who took a group and
  until when, set with `findAndModify`), so a group is handed over once.
- The pictures stored, so that the instance taking over a data source continues from the last
  picture rather than from the folder as it finds it.
- The answer to "is this data source being published" taken from the database wherever several
  instances run, by what the deployment is rather than by a setting left to whoever installs it.
