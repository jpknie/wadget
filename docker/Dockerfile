FROM alpine:3.20 AS build
RUN apk add --no-cache build-base meson ninja git openssl-dev   # <-- dev headers for build
WORKDIR /app
COPY . /app
RUN meson setup build --buildtype=release \
 && meson compile -C build

FROM alpine:3.20
RUN apk add --no-cache libstdc++ openssl   # <-- runtime libs: libssl, libcrypto
COPY --from=build /app/build/budgeteer /usr/local/bin/budgeteer
EXPOSE 8080
CMD ["budgeteer"]
