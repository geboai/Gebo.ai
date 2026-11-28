# Seeds a fresh Zammad for ZammadIngestionIntegrationTest: run with
#   bundle exec rails runner zammad-seed.rb
# It prints ADMIN_KEY=<api token of an agent able to read both groups and the
# knowledge base>. The data: the group "Gebo Support" (in the data source) with
# an open ticket holding a customer message, an internal note and an agent
# phone reply, the customer message carrying relay.log; a closed ticket; the group
# "Gebo Secret" (out of the data source) with its ticket; a knowledge base with
# a category, a sub-category, a published answer with an attachment, an
# internal answer, a draft and an archived answer.
UserInfo.current_user_id = 1
Setting.set('system_init_done', true)
Setting.set('fqdn', 'localhost')
Setting.set('http_type', 'http')
Setting.set('api_token_access', true)

support = Group.create!(name: 'Gebo Support', active: true)
secret = Group.create!(name: 'Gebo Secret', active: true)

admin = User.create!(login: 'gebo-admin@example.com', firstname: 'Gebo', lastname: 'Admin',
                     email: 'gebo-admin@example.com', password: "#{SecureRandom.hex(12)}Aa1!", active: true,
                     roles: Role.where(name: %w[Admin Agent]))
admin.group_names_access_map = { 'Users' => 'full', 'Gebo Support' => 'full', 'Gebo Secret' => 'full' }
admin.save!

org = Organization.create!(name: 'Acme Mail')
customer = User.create!(login: 'carla@example.com', firstname: 'Carla', lastname: 'Customer',
                        email: 'carla@example.com', organization: org, active: true,
                        roles: Role.where(name: 'Customer'))

web = Ticket::Article::Type.find_by!(name: 'web')
note = Ticket::Article::Type.find_by!(name: 'note')
phone = Ticket::Article::Type.find_by!(name: 'phone')
by_customer = Ticket::Article::Sender.find_by!(name: 'Customer')
by_agent = Ticket::Article::Sender.find_by!(name: 'Agent')
normal = Ticket::Priority.find_by!(name: '2 normal')
high = Ticket::Priority.find_by!(name: '3 high')

smtp = Ticket.create!(title: 'SMTP relay rejects large mails', group: support, customer: customer, owner: admin,
                      state: Ticket::State.find_by!(name: 'open'), priority: high)
first = Ticket::Article.create!(ticket: smtp, type: web, sender: by_customer,
                                from: 'Carla Customer <carla@example.com>', subject: smtp.title,
                                content_type: 'text/plain', internal: false,
                                body: "Mails over 10MB bounce since Monday.\nThe relay log is attached.")
Store.create!(object: 'Ticket::Article', o_id: first.id, filename: 'relay.log',
              data: "2026-11-02 09:14:03 relay[2231]: 552 5.3.4 message too large (12MB)\n",
              preferences: { 'Content-Type' => 'text/plain' })
Ticket::Article.create!(ticket: smtp, type: note, sender: by_agent, internal: true, content_type: 'text/html',
                        subject: 'internal', body: '<p>Confirmed on production too, the relay limit is <b>10MB</b>.</p>')
Ticket::Article.create!(ticket: smtp, type: phone, sender: by_agent, internal: false, content_type: 'text/html',
                        from: 'Gebo Support <support@example.com>', to: 'carla@example.com',
                        subject: "RE: #{smtp.title}",
                        body: '<p>We are raising the limit, a fix is planned for <i>Friday</i>.</p>')

limits = Ticket.create!(title: 'Document the mail limits', group: support, customer: customer,
                        state: Ticket::State.find_by!(name: 'closed'), priority: normal)
Ticket::Article.create!(ticket: limits, type: note, sender: by_agent, internal: false, content_type: 'text/plain',
                        subject: limits.title, body: 'Write the mail limits page in the help center.')

hidden = Ticket.create!(title: 'Secret bug', group: secret, customer: customer,
                        state: Ticket::State.find_by!(name: 'new'), priority: normal)
Ticket::Article.create!(ticket: hidden, type: note, sender: by_agent, internal: false, content_type: 'text/plain',
                        subject: hidden.title, body: 'Secret bug: nobody outside the secret group may read this.')

kb = KnowledgeBase.new(iconset: 'FontAwesome', color_highlight: '#38ae6a', color_header: '#f9fafb',
                       color_header_link: 'hsl(206,8%,50%)', homepage_layout: 'grid', category_layout: 'grid',
                       active: true)
# a knowledge base cannot be valid before its locale and translation exist, and
# saving them nested inserts the locale before the knowledge base: three steps
kb.save!(validate: false)
kb_locale = KnowledgeBase::Locale.create!(knowledge_base: kb, system_locale: Locale.find_by!(locale: 'en-us'),
                                          primary: true)
KnowledgeBase::Translation.create!(knowledge_base: kb, kb_locale: kb_locale, title: 'Gebo help center',
                                   footer_note: '')
kb.reload.save!

def seed_category(kb, kb_locale, title, parent = nil)
  c = KnowledgeBase::Category.new(knowledge_base: kb, parent: parent, category_icon: 'f115')
  c.translations.build(title: title, kb_locale: kb_locale)
  c.save!
  c
end

def seed_answer(category, kb_locale, title, body, **flags)
  a = KnowledgeBase::Answer.new(category: category, **flags)
  a.translations.build(title: title, kb_locale: kb_locale,
                       content: KnowledgeBase::Answer::Translation::Content.new(body: body))
  a.save!
  a
end

install = seed_category(kb, kb_locale, 'Install')
network = seed_category(kb, kb_locale, 'Network', install)
guide = seed_answer(install, kb_locale, 'Install guide', '<p>Unpack the bundle and <b>open the port 8080</b>.</p>',
                    published_at: Time.zone.now)
Store.create!(object: 'KnowledgeBase::Answer', o_id: guide.id, filename: 'install-checklist.txt',
              data: "1. open the browser on the gebo host\n2. log in as admin\n",
              preferences: { 'Content-Type' => 'text/plain' })
seed_answer(network, kb_locale, 'Proxy settings',
            '<p>Set the proxy for the relay in the <code>smtp</code> section.</p>', published_at: Time.zone.now)
seed_answer(network, kb_locale, 'Agents only: relay credentials', '<p>The relay credentials are in the vault.</p>',
            internal_at: Time.zone.now)
seed_answer(install, kb_locale, 'Draft upgrade notes', '<p>Draft text never published.</p>')
seed_answer(install, kb_locale, 'Old install guide', '<p>Archived text of an old release.</p>',
            published_at: 1.year.ago, archived_at: Time.zone.now)

token = Token.create!(action: 'api', persistent: true, user_id: admin.id, name: 'gebo-ingestion',
                      preferences: { permission: %w[ticket.agent knowledge_base.reader knowledge_base.editor] })
puts "ADMIN_KEY=#{token.token}"
puts "IDS support=#{support.id} secret=#{secret.id} kb=#{kb.id} install=#{install.id} network=#{network.id} smtp=#{smtp.id}"
