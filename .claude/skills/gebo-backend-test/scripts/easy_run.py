"""Full easy-mode setup run for one vendor, exactly as the wizard does it:
verify the api key, then createLLMByAutoconfigure with the library preset defaults,
then read the setup status back.

    python easy_run.py <vendorId> <secretId> [path/to/library.yml]

The secret id is the _id of the vendor's entry in the geboSecret mongo collection
(context = vendor id); look it up, do not invent it. Defaults for the library path
point at the repo's fast-setup library."""
import os
import sys
import yaml
from gebo import Session

DEFAULT_LIB = os.path.join(
    os.path.dirname(os.path.dirname(os.path.dirname(os.path.dirname(
        os.path.dirname(os.path.abspath(__file__)))))),
    "gebo.llms.parent", "gebo.llms.setup", "src", "main", "resources",
    "llms-fast-setup-library", "library.yml")
CTRL = "/api/admin/GeboFastLLMSSetupController"
FIELD = {"EMBEDDING": "embeddingModel", "RANKING": "rankerModel",
         "TRANSCRIPT": "transcriptModel", "TTS": "ttsModel", "IMAGESGEN": "imagesModel"}


def find_vendors(o):
    if isinstance(o, dict):
        for k, v in o.items():
            if k == "vendors":
                return v
            r = find_vendors(v)
            if r:
                return r
    return None


vid = sys.argv[1]
secret = sys.argv[2]
lib_path = sys.argv[3] if len(sys.argv) > 3 else DEFAULT_LIB
lib = find_vendors(yaml.safe_load(open(lib_path, encoding="utf-8")))
vendor = {v["vendorInfo"]["vendorId"]: v for v in lib}[vid]

s = Session()
print("=== easy mode run: %s ===" % vid)

# 1) verify credentials + download models (what the wizard fires on key select)
st, res = s.post(CTRL + "/verifyVendorCredentialsAndDownloadModels",
                 {"vendorId": vid, "secretId": secret, "baseUrl": None})
print("verifyVendorCredentials HTTP %s | hasErr=%s | models=%d"
      % (st, (res or {}).get("hasErrorMessages"), len(((res or {}).get("result")) or [])))

# 2) build the autoconfigure payload from the preset defaults, like the easy tab
payload = {"vendorId": vid, "secretId": secret}
for preset in vendor.get("presets") or []:
    kind = preset["type"]
    choices = preset.get("choices") or []
    default = next((c["code"] for c in choices if c.get("defaultChoice")), None)
    if kind == "CHAT":
        payload["defaultChatModel"] = default
        internal = next((c["code"] for c in choices
                         if "INTERNAL_SERVICES" in (c.get("uses") or [])), None)
        if internal:
            payload["internalServicesModel"] = internal
    elif kind in FIELD and default:
        payload[FIELD[kind]] = default
print("autoconfigure request:", {k: v for k, v in payload.items() if k != "secretId"})

st, res = s.post(CTRL + "/createLLMByAutoconfigure", payload)
created = [(c.get("modelTypeCode"), (c.get("choosedModel") or {}).get("code"))
          for c in ((res or {}).get("result") or [])]
print("createLLMByAutoconfigure HTTP %s | hasErr=%s | created=%d"
      % (st, (res or {}).get("hasErrorMessages"), len(created)))
for h, code in created:
    print("   + %-30s %s" % (h, code))
for m in (res or {}).get("messages") or []:
    print("   MSG [%s] %s: %s" % (m.get("severity"), m.get("summary"), str(m.get("detail"))[:150]))

# 3) final status
st, status = s.get(CTRL + "/getLLMSSetupStatus")
print("STATUS:")
for k in ['chat', 'internalServicesChat', 'embedded', 'ranking', 'images', 'tts', 'transcript']:
    print("  %-22s %-6s %-34s %s" % (k, (status or {}).get(k + 'ModelSetup'),
          (status or {}).get(k + 'ModelCode'), (status or {}).get(k + 'ModelProviderId')))
print("  isSetup:", (status or {}).get('isSetup'))
