package com.example.ishant.realdb;

/**
 * Created by Ishant on 25-06-2017.
 */

class ContactsConstructor {

    //private variables
    String _id;
    String _name;
    String _email;
    String _group;
    String _emergemcy;
    String _phone_number;

    // Empty constructor
    public ContactsConstructor() {

    }

    // constructor
    public ContactsConstructor(String id, String name, String email, String phno, String groups, String emergency) {
        this._id = id;
        this._name = name;
        this._phone_number = phno;
        this._email = email;
        this._group = groups;
        this._emergemcy = emergency;
    }

    //        // constructor
//        public ContactsConstructor(String name, String _phone_number) {
//            this._name = name;
//            this._phone_number = _phone_number;
//        }
//
//        // getting ID
//        public int getID() {
//            return this._id;
//        }
//
//        // setting id
//        public void setID(int id) {
//            this._id = id;
//        }
//
        // getting group
        public String getGroup() {
            return this._group;
        }
//
        // setting group
        public void setGroup(String group) {
            this._group = group;
        }

        // getting phone number
        public String getPhoneNumber() {
            return this._phone_number;
        }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }

}
