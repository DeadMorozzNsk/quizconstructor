import Vue from 'vue'
import axios from "axios"
import AxiosPlugin from 'vue-axios-cors'
import VueAxios from "vue-axios"
import '@babel/polyfill'

Vue.use(VueAxios, axios)
Vue.use(AxiosPlugin)

const instance = axios.create({
    baseURL: 'http://localhost:8181/',
    // withCredentials: true,
    headers: {
        accept: 'application/json'
    }
})

export default {
    /* returns array of all quizes */
    getAllFullDataQuizes: () => {
        return instance.get('quiz/all')
    },
    /* returns array of all quizes */
    getAllShortQuizes: () => {
        return instance.get('quiz/all/simplified')
    },
    /* adds new quiz to DB */
    addQuiz: (quiz) => {
        return instance.post('quiz/add_quiz', quiz)
    },
    /* updates existing quiz in DB */
    updateQuiz: (quiz) => {
        console.log('api reveived quiz')
        console.log(quiz)
        return instance.put('quiz/' + quiz.id, quiz)
    },
    /* deletes quiz by ID */
    deleteQuiz(quizId) {
        return instance.delete('quiz/' + quizId)
    },

    /* downloads quiz data */
    getQuizById: (quizId) => {
        return instance.get('quiz/' + quizId)
    },


}