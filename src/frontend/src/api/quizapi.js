export default function (instance) {
    return {
        getAllQuiz() {
            return instance.get('anketa/all')
        },
        setQuiz(payload) {
            return instance.post('anketa/add_quiz', payload)
        }
    }
}