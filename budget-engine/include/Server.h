
#include "httplib.h"

class Server {
    
    public:
        Server();
        void start();

    private:    
    httplib::Server svr;
};