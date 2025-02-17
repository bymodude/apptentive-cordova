var Apptentive = {
    initialized: false,

    addCustomDeviceData: function (successCallback, errorCallback, key, value) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["addCustomDeviceData", key, value]);
    },

    addCustomPersonData: function (successCallback, errorCallback, key, value) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["addCustomPersonData", key, value]);
    },

    deviceReady: function (successCallback, errorCallback) {
        console.log("Apptentive.deviceReady()");
        Apptentive.initialized = true;
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["deviceReady"]);
    },

    engage: function (successCallback, errorCallback, eventName, customData) {
        if (customData) {
            cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["engage", eventName, customData]);
        } else {
            cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["engage", eventName]);
        }
    },

    getUnreadMessageCount: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["unreadMessageCount"]);
    },

    pause: function (successCallback, errorCallback) {
        console.log("Apptentive.pause()");
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["pause"]);
    },

    putRatingProviderArg: function (successCallback, errorCallback, key, value) {
        successCallback(); // Does nothing on iOS.
    },

    removeCustomDeviceData: function (successCallback, errorCallback, key) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["removeCustomDeviceData", key]);
    },

    removeCustomPersonData: function (successCallback, errorCallback, key) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["removeCustomPersonData", key]);
    },

    resume: function (successCallback, errorCallback) {
        console.log("Apptentive.resume()");
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["resume"]);
    },

    setInitialUserEmail: function (successCallback, errorCallback, email) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["setProperty", "initialUserEmailAddress", email]);
    },

    setInitialUserName: function (successCallback, errorCallback, name) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["setProperty", "initialUserName", name]);
    },

    setRatingProvider: function (successCallback, errorCallback, ratingProviderName) {
        successCallback(); // Does nothing on iOS.
    },

    setUnreadMessagesListener: function (unreadMessagesCallback, errorCallback) {
        cordova.exec(unreadMessagesCallback, errorCallback, "ApptentiveBridge", "execute", ["registerForMessageNotifications"]);
    },

    showMessageCenter: function (successCallback, errorCallback, customData) {
        if (customData) {
            cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["showMessageCenter", customData]);
        } else {
            cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["showMessageCenter"]);
        }
    },

    willShowInteraction: function (successCallback, errorCallback, eventName) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["willShowInteraction", eventName]);
    },

    setProperty: function (successCallback, errorCallback, key, value) {
        cordova.exec(successCallback, errorCallback, "ApptentiveBridge", "execute", ["setProperty", key, value]);
    }
};

module.exports = Apptentive;
