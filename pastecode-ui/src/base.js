exports.install = function (Vue, option) {

    /**
     * @returns {{ 返回带token的请求头 }}
     */
    Vue.prototype.getHeaders = function() {
        let tokenNames  = localStorage.getItem("tokenName");
        let tokenValue = localStorage.getItem("tokenValue");

        let headers = {};

        headers[tokenNames] = tokenValue;

        return headers;
    }
}