from http import HTTPStatus
from openapi_server.models.user import User
from openapi_server.models.user_base import UserBase


user_last_id = 1
user_data = {}


def read_users ():
    global user_data
    users = [
        UserBase (id = user.id, firstname = user.firstname, lastname = user.lastname)
        for user in user_data.values ()
    ]
    return users, HTTPStatus.OK.value


def create_user (body):
    global user_last_id
    global user_data
    user = User.from_dict (body)
    user.id = user_last_id
    user_last_id += 1
    user_data [user.id] = user
    return None, HTTPStatus.CREATED.value


def delete_user (user_id):
    global user_data
    try:
        del user_data [user_id]
        return None, HTTPStatus.NO_CONTENT.value
    except KeyError:
        return None, HTTPStatus.NOT_FOUND.value


def read_user (user_id):
    global user_data
    try:
        return user_data [user_id], HTTPStatus.OK.value
    except KeyError:
        return None, HTTPStatus.NOT_FOUND.value


def update_user (user_id, body):
    global user_data
    user = User.from_dict (body)
    user.id = user_id
    if user.id in user_data:
        user_data [user.id] = user
        return None, HTTPStatus.RESET_CONTENT.value
    else:
        return None, HTTPStatus.NOT_FOUND.value
