import axios from 'axios'

const request = axios.create({
    baseURL: '/api',
    timeout: 60000,
    withCredentials: true
})

// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    // config.headers['token'] = window.localStorage.getItem("token");  // 设置请求头
    // hideLoading(); // 隐藏加载
    return config
}, error => {

    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        return response.data
    },
    error => {
        console.log('err' + error) // for debug
        return error
    }
)


export default request
