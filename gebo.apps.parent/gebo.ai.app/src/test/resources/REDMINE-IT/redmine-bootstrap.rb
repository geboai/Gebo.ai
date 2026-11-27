# Bootstrap of the throwaway Redmine of RedmineIngestionIntegrationTest:
# default trackers/statuses/roles, REST API on, and the admin API key printed
# for the test to read (the key exists only inside the test container).
Redmine::DefaultData::Loader.load('en') unless Tracker.exists?
Setting.rest_api_enabled = '1'
admin = User.find_by_login('admin')
admin.must_change_passwd = false
admin.save!
puts "ADMIN_KEY=#{admin.api_key}"
