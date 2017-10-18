db = db.getSiblingDB('gridfs')

db.createUser({
	user: "usr",
	pwd: "pwd",
	roles: ["readWrite"]
})