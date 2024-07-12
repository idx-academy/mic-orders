function fn(auth) {

   var credentials = karate.merge(auth || {});
   var managerCredentials = karate.merge(auth || {});
   // get password from system properties
   credentials.username = karate.get('credentials.username') || credentials.username
   credentials.password = karate.get('credentials.password') || credentials.password
   credentials.id = karate.get('credentials.id') || credentials.id
   credentials.authMode = karate.get('defaultAuthMode') || credentials.authMode;

  managerCredentials.username = karate.get('managerCredentials.username') || managerCredentials.username;
  managerCredentials.password = karate.get('managerCredentials.password') || managerCredentials.password;
  managerCredentials.id = karate.get('managerCredentials.id') || managerCredentials.id;
  managerCredentials.authMode = karate.get('defaultAuthMode') || managerCredentials.authMode;

  // returns auth header
  var tokenAuth = function(credentials) {
    var authHeadersCache = karate.properties['authHeadersCache'] || {};
    var authHeader = authHeadersCache[credentials.username];
    if (!authHeader) {
      var authResponse = karate.call('classpath:karate-auth.feature@fetchAuthToken', credentials);
      karate.logger.debug('>> auth  >> status       >>', authResponse.responseStatus)
      karate.logger.debug('>> auth  >> response     >>', authResponse.response)
      authHeadersCache[credentials.username] = authResponse.response['token'];
    }
    karate.properties['authHeadersCache'] = authHeadersCache;

    return { Authorization: 'Bearer ' + authHeadersCache[credentials.username] };
  };

  var incorrectToken = function(credentials) {
    return { Authorization: 'Bearer incorrectToken'};
  }

  var authModes = {
    token: tokenAuth,
    incorrectToken: incorrectToken,
  };

  var authModeFunction = authModes[credentials.authMode];
  var managerAuthModeFunction = authModes[managerCredentials.authMode];
  // calls auth function and return auth header
  return authModeFunction ? authModeFunction(credentials) : (managerAuthModeFunction ? managerAuthModeFunction(managerCredentials) : null);
}
