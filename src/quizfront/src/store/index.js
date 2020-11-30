import Vue from 'vue'
import Vuex from 'vuex'
import quizApi from "../api/quizapi"
import '@babel/polyfill'
import cors from 'vue-axios-cors'

Vue.use(Vuex)
Vue.use(cors)

export default new Vuex.Store({
    state: {
        user: Object,
        quizList: []
    },
    mutations: {
        setQuizList(state, quizList) {
            state.quizList = quizList;
        },

        addQuizMutation(state, quiz) {
            state.quizList = [
                ...state.quizList,
                quiz
            ]
        },

        updateQuizMutation(state, quiz) {
            const updateIndex = state.quizList.findIndex(item => item.id === quiz.id)
            //if (updateIndex === 0 || updateIndex === (state.quizList.length - 1)){
                state.quizList[updateIndex] = quiz
            // } else
            // state.quizList = [
            //     ...state.quizList.slice(0, updateIndex),
            //     quiz,
            //     ...state.quizList.slice(updateIndex + 1)
            // ]
        },

        deleteQuizMutation(state, quiz) {
            const deleteIndex = state.quizList.findIndex(item => item.id === quiz.id)
            state.quizList = [
                ...state.quizList.slice(0, deleteIndex),
                ...state.quizList.slice(deleteIndex + 1)
            ]
        },

        addQuestionMutation(state, payload) {
            const index = state.quizList.findIndex(item => item.id === payload.quizId)
            console.log(payload.question)
            state.quizList[index].questions.push(payload.question)
        }
    },
    actions: {
        async receiveAllQuiz({ commit }) {
            const result = await quizApi.getAllShortQuizes()
            commit('setQuizList', result.data)
        },

        async downloadQuizData({ commit }, quizID) {
            const result = await quizApi.getQuizById(quizID)
            // console.log('updateData=' + result.data)
            // console.log(result.data)
            commit('updateQuizMutation', result.data)
        },

        async addQuizAction({commit}, quiz) {
            const result = this.addQuiz(quiz)
            const data = await result.json()
            const index = this.quizList.findIndex(item => item.id === quiz.id)
            if (index > -1) {
                commit('updateQuizMutation', data)
            } else {
                commit('addQuizMutation', data)
            }
        },

        async updateQuizAction({commit, state}, quiz) {
            /*const result = */await quizApi.updateQuiz(quiz)
            // console.log(result.data)
            const index = state.quizList.findIndex(item => item.id === quiz.id)
            if (index > -1)
                commit('updateQuizMutation', quiz)
        },

        async deleteQuizAction({commit}, quiz) {
            const result = this.deleteQuiz(quiz.id)
            const data = await result.json()
            const index = this.quizList.findIndex(item => item.id === quiz.id)
            if (index > -1)
                commit('deleteQuizMutation', data)
        },

        addQuestionAction({ commit }, quizId) {
            const newQuestion = {
                id: 0,
                text:'Новый вопрос',
                quiz: quizId,
                position: 0,
                questionType:'MULTIPLE',
                answers:[]
            }
            if (quizId > -1)
                commit('addQuestionMutation', {
                    quizId: quizId,
                    question: newQuestion
                })
            else
                console.error('Wrong quiz index')
        }
    },
    modules: {},
    getters: {
        getQuizList: state => {
            return state.quizList
        },

        getUser: state => {
            return state.user
        }
    }
})
