import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        component: () => import('../Guide/Login')
    }, {
        path: '/register',
        component: () => import('../Guide/Register')
    }, {
        path: '/admin',
        component: () => import('../admin/Main'),
        children: [
            {
                path: 'announce',
                component: () => import('../admin/AnnounceManagement')
            }, {
                path: 'forum',
                component: () => import('../admin/ForumManagement')
            }, {
                path: 'seat',
                component: () => import('../admin/SeatManagement'),
                children: [
                    {
                        path: "number",
                        component: () => import('../admin/SeatNumber')
                    },
                    {
                        path: "crud",
                        component: () => import('../admin/SeatCrud')
                    }
                ]
            }, {
                path: 'student',
                component: () => import('../admin/StudentManagement')
            }, {
                path: 'teacher',
                component: () => import('../admin/TeacherManagement')
            }, {
                path: 'statistics',
                component: () => import('../admin/Statistics')
            },
        ]
    },
    {
        path: '/student',
        component: () => import('../user/Main'),
        children: [
            {
                path: 'forum',
                component: () => import('../user/Forum')
            },
            {
                path: 'seat',
                component: () => import('../user/Seat'),
                children: [
                    {
                        path: 'reservation',
                        component: () => import('../user/Reservation')
                    }, {
                        path: 'toSigned',
                        component: () => import('../user/ToSigned')
                    }, {
                        path: 'beUse',
                        component: () => import('../user/BeUse')
                    }
                ]
            }

        ]
    }
    , {
        path: '/LookStudentMess',
        component: () => import('../teacher/StudentMess')
    }, {
        path: '/ScoreMess',
        component: () => import('../user/ScoreMess')
    }, {
        path: '/PushArticle',
        component: () => import('../user/PushArticle')
    }, {
        path: '/ForumInfo',
        component: () => import('../user/ForumInfo')
    }, {
        path: '/Teacher',
        component: () => import('../teacher/Main'),
        children: [
            {
                path: 'seat',
                component: () => import('../teacher/SeatMess')
            }, {
                path: 'reservation',
                component: () => import('../teacher/ReservationMess')
            }, {
                path: 'score',
                component: () => import('../teacher/ScoreManagement')
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
