<template>
  <div id="admin-home" v-show="show" v-loading.fullscreen.lock="loading">
    <el-row>
      <el-col :span="6">
        <el-statistic title="代码分享总数" :value="shareTotal">
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic title="注册用户总数" :value="userTotal" />
      </el-col>
      <el-col :span="6">
        <el-statistic title="代码运行总数" :value="runTotal">
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic title="系统当前时间">
          <template slot="formatter">{{ date }}</template>
        </el-statistic>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <div id="chart">
      <div id="heatmapChart"
           :style="{ width: '1200px', height: '250px'}">
      </div>
      <div id="codeTypeChart"
           :style="{ width: '600px', height: '300px', float: 'left'}">
      </div>
      <div id="logTypeChart"
           :style="{ width: '600px', height: '300px', float: 'left'}">
      </div>
    </div>
  </div>
</template>

<script>

import moment from "moment";
import echarts from "echarts";

export default {
  name: "Home",
  data() {
    return {
      loading: true,
      show: false,
      userTotal: 0,
      shareTotal: 0,
      runTotal: 0,
      year: new Date().getFullYear(),
      codePieInfo: [],
      logPieInfo: [],
      codeHeatmapInfo: [],
      codeTypeTotal: '',
      codeCreateDateTotal: '',
      codeCreateDate: '',
      logTypeTotal: '',
      date: moment().format("YYYY-MM-DD HH:mm:ss"),
    }
  },
  async mounted() {
    await this.init();
    this.show = true;
    this.drawLine();
    this.drawLines();
    this.heatmap();
    this.timer = setInterval(() => {
      this.date = moment().format("YYYY-MM-DD HH:mm:ss");
    }, 100);
  },
  methods: {
    async init() {
      await this.$axios({
        method: 'get',
        url: `${this.$url}/user/getUserTotal`,
      }).then((response) => {
        if (response.data.flag) {
          this.userTotal = response.data.data;
        }
      });

      await this.$axios({
        method: 'get',
        url: `${this.$url}/code/getShareTotal`,
      }).then((response) => {
        if (response.data.flag) {
          this.shareTotal = response.data.data;
        }
      });

      await this.$axios({
        method: 'get',
        url: `${this.$url}/code/getRunTotal`,
      }).then((response) => {
        if (response.data.flag) {
          this.runTotal = response.data.data;
        }
      });

      await this.$axios({
        method: 'get',
        url: `${this.$url}/code/getCodePieInfo`,
      }).then((response) => {
        if (response.data.flag) {
          let list = response.data.data;
          this.codePieInfo = [];
          list.forEach((value) => {
            this.codePieInfo.push({
              "value": value.codeTypeTotal,
              "name" : value.codeType
            });
          });
        }});

      await this.$axios({
        method: 'get',
        url: `${this.$url}/code/getCodeDate/${this.year}`,
      }).then((response) => {
        if (response.data.flag) {
          let list = response.data.data;
          this.codeHeatmapInfo = [];
          list.forEach((value) => {
            this.codeHeatmapInfo[value.codeCreateDate] = value.codeCreateDateTotal;
          });

          console.log(this.codeHeatmapInfo)
        }
      });

      await this.$axios({
        method: 'get',
        url: `${this.$url}/log/getLogPieInfo`,
      }).then((response) => {
        if (response.data.flag) {
          let list = response.data.data;

          this.logPieInfo = [];
          list.forEach((value) => {
            this.logPieInfo.push({
              "value": value.logTypeTotal,
              "name" : value.logType
            });
          })
        }
      });


      setTimeout(() => {
        this.loading = false;
      }, 1000);

    },

    drawLine() {

      const echarts = require('echarts');
      console.log("abd");
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById('codeTypeChart'))
      // 绘制图表
      myChart.setOption({
        title: {
          text: '代码类型',
          left: 'center',
          textStyle: {
            fontSize: 20,
          }
        },
        tooltip: {
          trigger: 'item',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          textStyle: {
            fontSize: 20,
          }
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: this.codePieInfo,
            top: '-10',
            label: {
              fontSize: 20,
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      });
    },

    drawLines() {

      const echarts = require('echarts');
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById('logTypeChart'))
      // 绘制图表
      myChart.setOption({
        title: {
          text: '操作类型',
          left: 'center',
          textStyle: {
            fontSize: 20,
          }
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          textStyle: {
            fontSize: 20,
          }
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: this.logPieInfo,
            top: '-10',
            label: {
              fontSize: 20,
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      });
    },

    heatmap() {

      const echarts = require('echarts');

      let myChart = echarts.init(document.getElementById('heatmapChart'));

      myChart.setOption({
        title: {
          left: 'center',
          text: `${this.year}年代码提交日历`
        },
        tooltip: {
          trigger: 'item',
          formatter: function (p) {
            return `提交代码：${p.data[1]}<br>${echarts.format.formatTime('yyyy-MM-dd', p.data[0])}`;
          }
        },
        visualMap: {
          min: 0,
          max: 300,
          type: 'piecewise',
          orient: 'horizontal',
          left: 'center',
          top: 30
        },
        calendar: {
          top: 80,
          cellSize: ['auto', 13],
          range: new Date().getFullYear(),
          itemStyle: {
            borderWidth: 0.5
          },
          yearLabel: { show: false }
        },
        series: {
          type: 'heatmap',
          coordinateSystem: 'calendar',
          data: this.getVirtualData('2023'),
          labelLayout: {
            fontSize: 50,
          },
        }
      });
    },
    getVirtualData(year) {

      const echarts = require('echarts');

      const date = + echarts.time.parse(year + '-01-01');
      const end = + echarts.time.parse(+year + 1 + '-01-01');
      const dayTime = 3600 * 24 * 1000;
      const data = [];
      for (let time = date; time < end; time += dayTime) {

        const t = echarts.time.format(time, '{yyyy}-{MM}-{dd}', false);

        data.push([
            // format 第二个参数代表是否为 UTC 时间
          t, this.codeHeatmapInfo[t] !== 0 ? this.codeHeatmapInfo[t] : 0
        ]);
      }

      console.log(data);

      return data;
    }
  }
}
</script>

<style scoped>
>>> .head {
  font-size: 20px !important;
}
#chart {
  margin-top: 20px;
}
</style>