project('budget-engine', 'cpp', default_options: ['cpp_std=c++20'])

threads = dependency('threads')
openssl = dependency('openssl')

srcs = files('src/main.cpp',
             'src/BudgetEngine.cpp'
             )

# Directory where we'll drop certs
build_dir = meson.current_build_dir()

# Custom target to create a self-signed cert
certs = custom_target('dev-certs',
  output: ['cert.pem', 'key.pem'],
  command: [
    'openssl', 'req', '-x509', '-newkey', 'rsa:2048',
    '-keyout', 'key.pem', '-out', 'cert.pem',
    '-days', '365', '-nodes', '-subj', '/CN=localhost'
  ],
  build_by_default: true,
  depend_files: [],  # no inputs; always safe to regenerate if missing
  console: true
)

exe = executable('budget-engine', srcs,
  include_directories: include_directories('third_party','include'),
  dependencies: [threads, openssl],
  install: true
)


