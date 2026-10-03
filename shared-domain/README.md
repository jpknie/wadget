# Shared Kotlin domain

Both sibling Gradle builds include this directory as `:shared-domain`; keep
`shared-domain`, `compute-gateway`, and `frontend` beside one another.

`com.jn.domain.Tag` is the canonical model in both applications:

| Field | Type |
| --- | --- |
| `id` | `String` |
| `name` | `String` |
| `weight` | `Double` |
| `capCents` | `Long?` |
| `mandatory` | `Boolean` |
| `mandatoryCents` | `Long?` |

`Rule` and `MatchMode` retain the gateway's existing fields, enum values, and
relationship to `Tag`. The shared models have no persistence or UI dependencies.

Gateway JPA entities remain persistence-only representations. Services map them
to and from the shared models, preserving table names, column types, enum storage,
and the rule-to-tag relationship. Controllers use the shared models directly.

Frontend domain state and clients use `Tag`. Existing category screen labels and
HTTP endpoints are unchanged. Only Compose's slider boundary converts weights
to/from `Float`; the domain model stores `Double`. Frontend-generated IDs remain
random-long values, now represented as strings.

The module uses each including build's existing Kotlin plugin version and keeps
outputs under that build's own build directory. Its JVM target uses Java 11; JS
and the existing iOS targets are also available. Android consumes the JVM variant.

Tests cover canonical JSON serialization and gateway persistence/JSON round trips.
Gateway builds and tests, frontend JVM builds/tests, JS compilation, and shared
JVM/JS tests were verified. The full frontend build remains blocked by pre-existing
Android/iOS entry-point and incomplete iOS-client errors; these are outside this
domain-sharing refactor. Use a supported Gradle runtime (Java 21 for the frontend's
Gradle 8.13, rather than Java 24).