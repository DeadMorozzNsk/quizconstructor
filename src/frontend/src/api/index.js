import instance from "@/api/instance";
import quizapi from "@/api/quizapi";

export default {
    quiz: quizapi(instance)
}