<template>
  <div class="container">
    <div class="header-text">Статистика</div>
    <div class="line"></div>
    <div v-if="loaded">
      <div class="chart">
        <canvas class="canvas" id="myChart"></canvas>
      </div>
      <div class="values">
        <div class="cont-val">
          <div class="values-wrapper">
            <div class="values-header">Количество постов</div>
            <div class="values-value">{{data.all_posts || 0}}</div>
          </div>
          <div class="values-wrapper">
            <div class="values-header">Количество лайков</div>
            <div class="values-value">{{data.all_likes}}</div>
          </div>
          <div class="values-wrapper">
            <div class="values-header">Дата регистрации</div>
            <div class="values-value">{{data.first_date}}</div>
          </div>
        </div>
        <div class="cont-val">
          <div class="values-wrapper">
            <div class="values-header">Среднее количество лайков</div>
            <div class="values-value">{{(data.avg_likes + '').substring(0, 4)}}</div>
          </div>
          <div class="values-wrapper">
            <div class="values-header">Среднее число посещений</div>
            <div class="values-value">{{ (data.avg_visit + '').substring(0, 4) }}</div>
          </div>
          <div class="values-wrapper">
            <div class="values-header">Самый посещаемый день</div>
            <div class="values-value">{{ data.popular_day}}</div>
          </div>
        </div>
      </div>
    </div>
    <LoadingComponent style="margin-top: 200px" v-else/>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import Chart from 'chart.js/auto';
import LoadingComponent from "@/components/LoadersComponents/LoadingComponent.vue";
let loaded = ref(false)
let data = ref('')
onMounted( async () => {
  await getStats()
})
const getStats = async () => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch('http://localhost:8090/stats/list', {
      method: 'POST',
      headers: {
        Authorization: token
      }
    });
    if (response.ok) {
      loaded.value = true
      data.value = await response.json();
      createChart()
    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
};
const createChart = () => {
  const ctx = document.querySelector('#myChart').getContext('2d');
  const labels = Object.keys(data.value.visit_data);
  const datas = Object.values(data.value.visit_data);
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Количество посещений',
        data: datas,
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          ticks: {
            stepSize: 1
          }
        }
      }
    }
  });
};

</script>

<style scoped>
.header-text {
  font-size: 22px;
  margin-left: 50px;
  color: var(--text-color-2);
  margin-bottom: 20px;
  padding-top: 30px;
}
.line {
  width: 93%;
  border: var(--border-1);
  margin: 0 auto 40px;
}
.container {
  width: var(--centr-size);
  background-color: var(--block-color-1);
  margin-top: 10px;
  border-radius: 5px;
  height: 600px;
  border: var(--border-1);
}
.chart {
  width: 600px;
  margin: 10px auto;
  border-radius: 10px;
}
.canvas {
  width: 300px;
}
.values {
  display: flex;
  width: 500px;
  margin-top: 40px;
  margin-left: 40px;
}
.values-wrapper {
  margin-bottom: 10px;
  display: flex;
}
.values-header {
  font-size: 15px;
  border: var(--border-1);
  padding: 4px;
  width: 200px;
  border-radius: 5px;
  color: var(--text-color-6);
  text-align: center;
  margin-right: 10px;
}
.values-value {
  width: 100px;
  font-size: 15px;
  padding-left: 10px;
  padding-right: 10px;
  color: var(--text-color-6);
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--hover-block-2);
  border-radius: 5px;
  border: var(--border-1);
}
.cont-val {
  width: 380px;
  margin-right: 35px;
}
</style>