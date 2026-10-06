
#include "httplib.h"
#include "json/json.hpp"
#include "BudgetEngine.h"

class Server {
    
    public:
        Server();
        void start();

    private:
        nlohmann::json createAllocationsResponse(const std::vector<AllocationResult>& allocations);
        httplib::Server svr;
};