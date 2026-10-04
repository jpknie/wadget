#include "Server.h"


Server::Server() {    
    svr.Get("/hi", [](const httplib::Request &, httplib::Response &res) {
        res.set_content("Hello World!", "text/plain");
    });
}

void Server::start() {
    svr.listen("0.0.0.0", 8080);
}