#include "Server.h"
#include <memory>
#include "json/json.hpp"


Server::Server() {

    svr.Get("/hi", [](const httplib::Request &, httplib::Response &res) {
        res.set_content("Hello World!", "text/plain");
    });

    svr.Post("/compute", [](const httplib::Request &req, httplib::Response &res) {
        auto body = req.body;
        auto json = nlohmann::json::parse(body);
        nlohmann::json output;
        output["income"] = 3500.0;
        // Here you can add logic to process the request body and perform computations
        res.set_content(output.dump(), "application/json");
    });
}

void Server::start() {
    svr.listen("0.0.0.0", 8080);
}