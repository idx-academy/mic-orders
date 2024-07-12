function fn() {
  function read(file) {
    try {
      return karate.read(file);
    } catch (e) {
      karate.log("File not found: " + file)
      return {};
    }
  }

  // Config - Common
  karate.set(read('classpath:config.yml'));
  // Config - Environment
  karate.set(read(`classpath:config-${karate.env}.yml`));
  // Config - Environment Secrets

  let secretConfig = read(`classpath:config-${karate.env}-secrets.yml`);
  karate.set(secretConfig);

  //Config datasource
  let datasource = {
    url: secretConfig.datasource.url,
    username: secretConfig.datasource.username,
    password: secretConfig.datasource.password
  };

  // Reporting Flags
  let showLogOption = false;
  let showAllStepsOption = true;
  if (karate.properties['karate.report.options']) {
    if (karate.properties['karate.report.options'].includes('--showLog true')) { showLogOption = true }
    if (karate.properties['karate.report.options'].includes('--showLog false')) { showLogOption = false }
    if (karate.properties['karate.report.options'].includes('--showAllStepsOption true')) { showAllStepsOption = true }
    if (karate.properties['karate.report.options'].includes('--showAllStepsOption false')) { showAllStepsOption = false }
  }
  karate.log('Report Verbosity showLog[', showLogOption, '] showAllSteps[', showAllStepsOption, ']')

  karate.configure('report', { showLog: showLogOption, showAllSteps: showAllStepsOption });

  karate.log('Running Feature[', karate.feature.name, '] Scenario [', karate.scenario.name, '] ...')

  return {
    utils: karate.call('classpath:karate-utils.js'),
  }
}
