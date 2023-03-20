<template>
  <div style="width: 200vw;height: 200vh;background-color: #78b3ef">
    <div class="login-box">
      <h2>注册账号</h2>
      <form>
        <div class="user-box">
          <input type="number" name="" required="" v-model="number">
          <label>学工号</label>
        </div>
        <div class="user-box">
          <input type="text" name="" required="" v-model="username">
          <label>姓名</label>
        </div>
        <div class="user-box">
          <input type="password" name="" required="" v-model="password">
          <label>密码</label>
        </div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <div style="display: flex;justify-content: center;align-items: center">
          <el-radio-group v-model="type">
            <el-radio-button label="学生"></el-radio-button>
            <el-radio-button label="老师"></el-radio-button>
          </el-radio-group>
        </div>

        <a @click="handleSubmit">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          注册
        </a>
        <a @click="$router.replace('/login')">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          登录
        </a>
      </form>
    </div>
  </div>
</template>

<script>
import request from "@/req";
import {Toast} from "vant";

export default {
  name: "Register",
  data() {
    return {
      type: '学生',
      number: 0,
      username: '',
      password: '123456789',
    }
  },
  methods: {
    handleSubmit() {
      if (this.number===0||this.username===''||this.password===''){
        Toast.fail('请完整输入')
        return
      }
      request.post('/public/register', {
        number: this.number,
        username: this.username,
        password: this.password,
        type: this.type === '学生' ? 0 : 1
      }).then(res => {
        Toast('注册成功')
        this.$router.replace('/login')
      })
    },
  },
}
;
</script>

<style scoped>


.login-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: #409eff;
  box-sizing: border-box;
  border-radius: 5px;
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.login-box .user-box {
  position: relative;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}

.login-box .user-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: .5s;
}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: white;
  font-size: 12px;
}

.login-box form a {
  border: 1px rgba(255, 255, 255, .3) solid;
  position: relative;
  display: inline-block;
  padding: 10px 100px;
  color: white;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: .5s;
  margin-top: 20px;
  margin-left: calc(50% - 120px);
  letter-spacing: 4px
}

.login-box a span {
  position: absolute;
  display: block;
}


</style>
