#!/bin/bash
# Seeds a freshly auto-installed GLPI 11 for GlpiIngestionIntegrationTest.
# Runs INSIDE the GLPI container (execInContainer), so every call goes to
# localhost: the legacy REST API is only open to 127.0.0.1 by default.
#
# usage: seed-glpi.sh <glpi user> <glpi password> <oauth client secret> <attachments dir> [entity name]
# prints KEY=VALUE lines: ENTITY_ID, CLIENT_ID, TICKET_IDS, KB_ARTICLE_IDS, DOCUMENT_IDS
set -euo pipefail

GLPI_USER="$1"
GLPI_PASSWORD="$2"
CLIENT_SECRET="$3"
ATTACHMENTS="$4"
ENTITY_NAME="${5:-Gebo IT test entity}"
CONSOLE="php /var/www/glpi/bin/console --no-interaction"
LEGACY="http://localhost/apirest.php"
V2="http://localhost/api.php"

# field of the JSON read on stdin, "" when absent
json() { php -r '$d = json_decode(stream_get_contents(STDIN), true); $v = $d; foreach (array_slice($argv, 1) as $k) { $v = is_array($v) && array_key_exists($k, $v) ? $v[$k] : ""; } echo is_scalar($v) ? $v : "";' -- "$@"; }
fail() { echo "SEED-ERROR: $*" >&2; exit 1; }
need_id() { [[ "$1" =~ ^[0-9]+$ ]] || fail "$2: $3"; }

# 1) the high-level API v2 (read by the handler) and the legacy API (file upload,
# OAuth client), with credentials login on the legacy one
$CONSOLE config:set enable_hlapi 1 >/dev/null
$CONSOLE config:set enable_api 1 >/dev/null
$CONSOLE config:set enable_api_login_credentials 1 >/dev/null
$CONSOLE cache:clear >/dev/null

SESSION=$(curl -s -u "$GLPI_USER:$GLPI_PASSWORD" "$LEGACY/initSession" | json session_token)
[ -n "$SESSION" ] || fail "legacy initSession refused"
legacy() { curl -s -X "$1" "$LEGACY/$2" -H "Session-Token: $SESSION" -H "Content-Type: application/json" ${3:+-d "$3"}; }

# 2) OAuth client for the password grant. GLPI generates identifier and
# secret on add; the update stores the known secret (GLPIKey-encrypted by
# OAuthClient::prepareInputForUpdate, which also rewrites redirect_uri).
ANSWER=$(legacy POST OAuthClient '{"input":{"name":"Gebo integration test","is_active":1,"is_confidential":1,"grants":["password"],"scopes":["api"]}}')
OAUTH_ID=$(echo "$ANSWER" | json id)
need_id "$OAUTH_ID" "OAuth client not created" "$ANSWER"
legacy PUT "OAuthClient/$OAUTH_ID" "{\"input\":{\"secret\":\"$CLIENT_SECRET\",\"redirect_uri\":[\"/api.php/oauth2/redirection\"]}}" >/dev/null
CLIENT_ID=$(legacy GET "OAuthClient/$OAUTH_ID" | json identifier)
[ -n "$CLIENT_ID" ] || fail "OAuth client identifier not readable"

TOKEN=$(curl -s -X POST "$V2/token" -d grant_type=password -d "client_id=$CLIENT_ID" \
	--data-urlencode "client_secret=$CLIENT_SECRET" -d "username=$GLPI_USER" \
	--data-urlencode "password=$GLPI_PASSWORD" -d scope=api | json access_token)
[ -n "$TOKEN" ] || fail "OAuth password grant refused"
v2() {
	local answer id
	answer=$(curl -s -X POST "$V2$1" -H "Authorization: Bearer $TOKEN" -H "GLPI-API-Version: 2.3.0" \
		-H "Content-Type: application/json" -d "$2")
	id=$(echo "$answer" | json id)
	need_id "$id" "POST $1 failed" "$answer"
	echo "$id"
}

# 3) contents, in an entity of their own under the root entity
ENTITY_ID=$(v2 /Administration/Entity "{\"name\":\"$ENTITY_NAME\",\"parent\":{\"id\":0}}")

TICKET_VPN=$(v2 /Assistance/Ticket "{\"name\":\"VPN drops every evening\",\"content\":\"<p>The <b>VPN</b> connection of the sales office drops every evening around 18:00.</p>\",\"entity\":{\"id\":$ENTITY_ID},\"type\":1,\"urgency\":4}")
v2 "/Assistance/Ticket/$TICKET_VPN/Timeline/Followup" '{"content":"<p>The firewall log shows the IKE rekey failing at 18:00.</p>"}' >/dev/null
v2 "/Assistance/Ticket/$TICKET_VPN/Timeline/Followup" '{"content":"<p>Rekey lifetime set to 8 hours on both peers, waiting for tonight.</p>"}' >/dev/null
v2 "/Assistance/Ticket/$TICKET_VPN/Timeline/Task" '{"content":"<p>Check the VPN tunnel again tomorrow morning.</p>","duration":1800}' >/dev/null
v2 "/Assistance/Ticket/$TICKET_VPN/Timeline/Solution" '{"content":"<p>Aligning the IKE rekey lifetime on both peers solved the evening drops.</p>"}' >/dev/null

TICKET_LAPTOP=$(v2 /Assistance/Ticket "{\"name\":\"New laptop for the warehouse manager\",\"content\":\"<p>Please prepare a laptop with the warehouse management client installed.</p>\",\"entity\":{\"id\":$ENTITY_ID},\"type\":2,\"urgency\":3}")
v2 "/Assistance/Ticket/$TICKET_LAPTOP/Timeline/Followup" '{"content":"<p>Laptop ordered, delivery expected next week.</p>"}' >/dev/null

KB_PRINTER=$(v2 /Knowledgebase/Article "{\"name\":\"How to reset the office printer\",\"content\":\"<p>Hold the reset button for ten seconds, then print the network page.</p>\",\"entity\":{\"id\":$ENTITY_ID}}")
KB_WIFI=$(v2 /Knowledgebase/Article "{\"name\":\"Guest Wi-Fi password rotation\",\"content\":\"<p>The guest Wi-Fi password changes every Monday and is posted at the reception.</p>\",\"entity\":{\"id\":$ENTITY_ID}}")

# 4) attachments: the v2 API has no upload, the legacy one stores the file and
# links it to the ticket (Document::post_addItem with itemtype/items_id). A new
# legacy session, because a session sees only the entities existing at login.
curl -s "$LEGACY/killSession" -H "Session-Token: $SESSION" >/dev/null || true
SESSION=$(curl -s -u "$GLPI_USER:$GLPI_PASSWORD" "$LEGACY/initSession" | json session_token)
[ -n "$SESSION" ] || fail "legacy initSession refused"
upload() {
	local file="$1" ticket="$2" answer id
	answer=$(cd "$ATTACHMENTS" && curl -s -X POST "$LEGACY/Document" -H "Session-Token: $SESSION" \
		-F "uploadManifest={\"input\":{\"name\":\"$file\",\"entities_id\":$ENTITY_ID,\"itemtype\":\"Ticket\",\"items_id\":$ticket,\"_filename\":[\"$file\"]}};type=application/json" \
		-F "filename[0]=@$file")
	id=$(echo "$answer" | json id)
	need_id "$id" "upload of $file failed" "$answer"
	echo "$id"
}
DOC_LOG=$(upload vpn-firewall-log.txt "$TICKET_VPN")
DOC_PDF=$(upload v4man.pdf "$TICKET_LAPTOP")

curl -s "$LEGACY/killSession" -H "Session-Token: $SESSION" >/dev/null || true

echo "ENTITY_ID=$ENTITY_ID"
echo "CLIENT_ID=$CLIENT_ID"
echo "TICKET_IDS=$TICKET_VPN,$TICKET_LAPTOP"
echo "KB_ARTICLE_IDS=$KB_PRINTER,$KB_WIFI"
echo "DOCUMENT_IDS=$DOC_LOG,$DOC_PDF"
