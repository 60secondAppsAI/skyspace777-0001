module.exports = {
  chainWebpack: config => {
    config
    .plugin('html')
    .tap(args => {
      args[0].title = 'skyspace777'
      return args
    })
  }
}
//const { defineConfig } = require("@vue/cli-service");    
//module.exports = defineConfig({
//  transpileDependencies: true,
//});
