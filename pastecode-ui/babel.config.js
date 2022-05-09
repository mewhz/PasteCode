module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
    plugins: [
        ['prismjs', {
          'languages': ['java', 'c', 'cpp', 'python'],
          'plugins': ['line-numbers', 'match-braces', 'show-language'],
          'theme': 'coy',
          'css': true
        }]
    ]
}
