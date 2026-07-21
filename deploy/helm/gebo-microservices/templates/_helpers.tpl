{{/* Chart name / fullname */}}
{{- define "gebo.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "gebo.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name (include "gebo.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{/* Common labels */}}
{{- define "gebo.labels" -}}
helm.sh/chart: {{ printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/part-of: gebo-microservices
{{- end -}}

{{/*
  Image reference for a backend/edge service.
  Usage: {{ include "gebo.image" (dict "root" $ "svc" "brain") }}
  -> {{ registry }}{{ namespace }}/brain.gebo.ai:{{ tag }}
*/}}
{{- define "gebo.image" -}}
{{- $img := .root.Values.image -}}
{{- printf "%s%s/%s.gebo.ai:%s" $img.registry $img.namespace .svc $img.tag -}}
{{- end -}}

{{/*
  Topology microservice-id for a service short name: "aws-s3" -> "aws_s3_gebo_ai".
  Usage: {{ include "gebo.microserviceId" "aws-s3" }}
*/}}
{{- define "gebo.microserviceId" -}}
{{- printf "%s_gebo_ai" (. | replace "-" "_") -}}
{{- end -}}

{{/*
  Whether a backend is deployed: mandatory ones always; optional ones unless enabled:false.
  Usage: {{ if (include "gebo.backendEnabled" $svc) }}  ($svc is the per-service values dict)
  Returns "true" or "" (empty is falsy).
*/}}
{{- define "gebo.backendEnabled" -}}
{{- if .mandatory -}}
true
{{- else if ne (.enabled | toString) "false" -}}
true
{{- end -}}
{{- end -}}

{{/*
  Fail the install if a mandatory backend was explicitly disabled.
  Usage: {{ include "gebo.assertMandatory" (dict "name" $name "svc" $svc) }}
*/}}
{{- define "gebo.assertMandatory" -}}
{{- if and .svc.mandatory (eq (.svc.enabled | toString) "false") -}}
{{- fail (printf "backends.%s is mandatory and cannot be disabled (enabled:false)" .name) -}}
{{- end -}}
{{- end -}}

{{/*
  Common backend env block (mirrors compose x-microservice + secret envs).
  Usage: {{ include "gebo.backendEnv" $ | nindent 12 }}
*/}}
{{- define "gebo.backendEnv" -}}
- name: EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
  value: "http://{{ .Release.Name }}-eureka:{{ .Values.eureka.port }}/eureka/"
- name: EUREKA_INSTANCE_PREFERIPADDRESS
  value: "true"
- name: SPRING_CONFIG_ADDITIONAL_LOCATION
  value: "file:/opt/gebo.ai/config/"
- name: JAVA_TOOL_OPTIONS
  value: {{ .Values.common.javaToolOptions | quote }}
- name: MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE
  value: {{ .Values.common.managementExposure | quote }}
{{- $otlp := .Values.common.otlpTracingEndpoint -}}
{{- if .Values.observability.enabled -}}
{{- $otlp = printf "http://%s-otel-collector:4318/v1/traces" .Release.Name -}}
{{- end }}
{{- if $otlp }}
- name: MANAGEMENT_TRACING_SAMPLING_PROBABILITY
  value: {{ .Values.common.tracingSamplingProbability | quote }}
- name: MANAGEMENT_OTLP_TRACING_ENDPOINT
  value: {{ $otlp | quote }}
{{- end }}
{{- with .Values.common.extraEnv }}
{{ toYaml . }}
{{- end }}
{{- end -}}

{{/* Name of the Secret backends read via envFrom. */}}
{{- define "gebo.secretName" -}}
{{- .Values.secrets.existingSecret | default (printf "%s-secrets" .Release.Name) -}}
{{- end -}}
