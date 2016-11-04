from pathfinders import app
from pathfinders.models import *
from flask import request

@app.before_request
def pre_request():
    ua = request.headers['User-Agent']
    ip = request.remote_addr
    print 'GET', request.path, ip, ua

@app.route('/', methods=['GET'])
def home():
    return app.send_static_file('documentation.html')
