import datetime
from flask import Flask, request, make_response, session, Response, jsonify, render_template
import json
app = Flask(__name__)
# cors = CORS(app)
app.secret_key = '\xb2pz\x97~\x9bV\xf1\xc7\xd6\x8f\x8e\xd7\xc6j?\x9f^\xa9\xbf\\\xe3\xf9\xc6'
# CORS(app, resources=r'/api/*', headers='Content-Type')

@app.route('/')
def root():
  return render_template('testing.html')

@app.route('/config', methods=['POST', 'GET'])
def login():
  if request.method == 'GET':
    return  Response(response=getFile(),
                    status=200,
                    mimetype="application/json")

  if request.method == 'POST':
    print "----------22"
    addToFile(json.loads(request.data))


    return  Response(response="Success",
                    status=200,
                    mimetype="text/plain")
  return  Response(response="Fail",
                    status=400,
                    mimetype="text/plain")


def getFile():

  with open('savedCommands', 'r') as f:
    read_data = f.read()

  return read_data

def addToFile(obj):
  saved = json.loads(getFile());
  print saved 
  print "----------"
  saved[obj['name']] = obj['commands']

  flatJson = json.dumps(saved);

  with open('savedCommands', 'w') as f:
    read_data = f.write(flatJson)


if __name__ == '__main__':
  app.run(debug=True, port=8000)

