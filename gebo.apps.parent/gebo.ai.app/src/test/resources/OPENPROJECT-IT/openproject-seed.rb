# Test data of OpenProjectIngestionIntegrationTest, run once with rails runner
# inside a fresh openproject container. The API v3 cannot create documents,
# meetings or agenda items, so the whole seed goes through the models.
# Prints the admin API key as ADMIN_KEY=<key>, the admin's id as ADMIN_ID=<id>,
# the SMTP work package as SMTP_WORK_PACKAGE=<id> and its internal comment as
# INTERNAL_COMMENT=<activity id>.
admin = User.find_by!(login: "admin")
admin.update_columns(force_password_change: false)
User.current = admin

def attach(container, author, name, content)
  file = OpenProject::Files.create_uploaded_file(name: name, content: content, content_type: "text/plain")
  Attachment.new(container: container, author: author, file: file, description: "").save!
end

def project(identifier, name, modules, parent = nil)
  Project.create!(workspace_type: "project", name: name, identifier: identifier, public: false,
    parent: parent, enabled_module_names: modules)
end

modules = %w[work_package_tracking documents meetings news]
demo = project("gebo-demo", "Gebo demo", modules)
sub = project("gebo-demo-sub", "Gebo demo sub", %w[work_package_tracking news], demo)
secret = project("gebo-secret", "Gebo secret", modules)

task = Type.find_by!(name: "Task"); bug = Type.find_by!(name: "Bug"); feature = Type.find_by!(name: "Feature")
st_new = Status.find_by!(name: "New"); st_prog = Status.find_by!(name: "In progress")
st_closed = Status.find_by!(name: "Closed")
prio = IssuePriority.default || IssuePriority.first

# work packages are saved without validation: OpenProject 17 enables the types of
# a project through type variants, not needed to read them back
def wp(project, type, status, prio, author, subject, description, parent = nil)
  w = WorkPackage.new(project: project, type: type, status: status, priority: prio, author: author,
    subject: subject, description: description, parent: parent)
  w.save!(validate: false)
  w
end

smtp = wp(demo, bug, st_prog, prio, admin, "SMTP relay drops attachments over 10 MB",
  "Mails with **large attachments** are silently dropped by the relay.")
attach(smtp, admin, "relay.log", "2026-11-20 10:00 relay: message too large (12MB), dropped\n")
child = wp(demo, task, st_new, prio, admin, "Raise the relay size limit", "Set `message_size_limit` to 25 MB.", smtp)
wp(demo, task, st_closed, prio, admin, "Document the mail limits", "Closed: written in the admin guide.", child)
pdf = wp(demo, feature, st_new, prio, admin, "PDF export of the meeting minutes", "Users want the minutes as PDF.")
Relation.create!(from: pdf, to: smtp, relation_type: "relates")
smtp.journal_notes = "Confirmed on production too, the limit is 10 MB."
smtp.journal_user = admin
smtp.save!(validate: false)
# an internal comment (a restricted journal), for the roles with view_internal_comments only
smtp.journal_notes = "Internal: the customer is still on the old contract, keep the relay limit until March."
smtp.journal_user = admin
smtp.journal_internal = true
smtp.save!(validate: false)
internal_comment = Journal.where(journable_type: "WorkPackage", journable_id: smtp.id, restricted: true)
  .order(:id).last
wp(sub, task, st_new, prio, admin, "Sub project task about slash commands", "Slash commands in the chat window.")
wp(secret, bug, st_new, prio, admin, "Secret bug outside the data source", "Not selected by the data source.")

doc_kind = defined?(DocumentType) ? { type: DocumentType.find_by!(name: "Documentation") } :
  { category: DocumentCategory.find_by!(name: "Documentation") }
doc = Document.create!(**doc_kind, project: demo, title: "Install guide",
  description: "# Install\n\nRun `docker compose up` and open the port 8080.")
attach(doc, admin, "install-checklist.txt", "1. docker\n2. compose\n3. open the browser on the gebo host\n")

meeting = Meeting.create!(project: demo, title: "Weekly sync", author: admin, location: "Room 1",
  start_time: Time.zone.parse("2026-11-24 10:00"), duration: 1.0)
section = meeting.sections.first || meeting.sections.create!(title: "Agenda")
meeting.agenda_items.create!(title: "SMTP relay incident", notes: "Decide the new size limit.", author: admin,
  meeting_section: section, duration_in_minutes: 15, item_type: :simple)
attach(meeting, admin, "minutes-draft.txt", "Decided: a 25 MB limit for the relay.\n")

News.create!(project: demo, title: "Gebo demo goes live", summary: "Launch notice",
  description: "The demo instance is live from today.", author: admin)

Token::API.where(user: admin).destroy_all
puts "ADMIN_KEY=#{Token::API.create!(user: admin).plain_value}"
puts "ADMIN_ID=#{admin.id}"
puts "SMTP_WORK_PACKAGE=#{smtp.id}"
puts "INTERNAL_COMMENT=#{internal_comment.id}"
