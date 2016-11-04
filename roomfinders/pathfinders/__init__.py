from flask import Flask
from flask_cache import Cache
from flask_sslify import SSLify
import os

app = Flask(__name__)
app.cache = Cache(app, config={'CACHE_TYPE': 'simple'})

if 'DYNO' in os.environ: # Only trigger SSLify if the app is running on Heroku
    sslify = SSLify(app)
else:
    app.debug = True # Only debug when running locally
    app.use_reloader = True

import pathfinders.endpoints
import pathfinders.private
