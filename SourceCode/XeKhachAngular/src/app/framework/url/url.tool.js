var fs = require('fs');
let url = require('../../business/url.declare.json');
fs.readFile('./url.declare.ts', function(err, data) {
  const firstContent = data.toString()
  splitContent = firstContent.split('  // ****@@@****');
  console.log(splitContent[1]);
  Object.keys(url)


});
// console.log(json);




