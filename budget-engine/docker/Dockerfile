FROM alpine:3.20 AS build
RUN apk add --no-cache build-base meson ninja git openssl-dev openssl   # <-- dev headers for build
WORKDIR /app
COPY . /app
RUN meson setup build --buildtype=release \
 && meson compile -C build

FROM alpine:3.20
RUN apk add --no-cache libstdc++ openssl   # <-- runtime libs: libssl, libcrypto
COPY --from=build /app/build/budget-engine /usr/local/bin/budget-engine
EXPOSE 8080
CMD ["budget-engine"]
