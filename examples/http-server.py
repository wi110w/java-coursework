#!/usr/bin/env python3
#
# Small HTTP server for testing purposes
# Just run it with python and it will serve it's directory contents
#
import http.server
import socketserver
import os

PORT = 8000

web_dir = os.path.dirname(__file__)
if web_dir:
    os.chdir(web_dir)

Handler = http.server.SimpleHTTPRequestHandler
httpd = socketserver.TCPServer(("", PORT), Handler)

print("serving at port", PORT)
try:
    httpd.serve_forever()
except KeyboardInterrupt:
    pass

httpd.server_close()
