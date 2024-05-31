function fn(auth) {

   var credentials = karate.merge(auth || {});
   // get password from system properties
   credentials.username = credentials.username || karate.get('credentials.username')
   credentials.password = credentials.password || karate.get('credentials.password')
   credentials.authMode = credentials.authMode || karate.get('defaultAuthMode');

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
  // calls auth function and return auth header
  return authModeFunction ? authModeFunction(credentials) : null;
}
