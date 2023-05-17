<template>
  <div>
    <table>
      <tr>
        <td>
          <table>
            <tr>
              <td>
                <h1>404</h1>
                <h3>大事不妙啦！</h3>
                <p>你访问的页面不存在或已被删除~<br/>
                </p>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "NotFound"
}
</script>

<style>
* {
  font-family: "Microsoft Yahei",system-ui;
  margin: 0;
  font-weight: lighter;
  text-decoration: none;
  text-align: center;
  line-height: 2.2em;
}

html, body {
  height: 100%;
}

h1 {
  font-size: 100px;
  line-height: 1em;
}

table {
  width: 100%;
  height: 100%;
  border: 0;
  border-collapse: collapse;
}

th, td {
  padding: 0.3em 1em;
}
</style>