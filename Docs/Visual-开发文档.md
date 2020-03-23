
# VISUAL界面API文档

* `/api/visual/index/submit`

    @GET，获取最近30天的提交统计

    ```json
    {
        "submit": [
            [
                long,int //分别对应x轴的时间（ms）和y轴的提交次数
            ],
        ],
        "ac": [
            [
                long,int //分别对应x轴的时间（ms）和y轴的AC次数
            ]
        ]
    }
    ```

* `/api/visual/user/rating?uid={uid}`

    @GET，获取用户rating变化曲线

    ```json
    {
        "state": int,// 0->success,1->user not exists
        "username": str,//name
        "contest": [
            {
                "cid": int,
                "starttime": int,
                "title": str,
                "score": int, // 比赛后分数
                "rank": int, //排名
                "sum": int, //总参与排名人数
                "addscore": int, //增加分数
            },
        ]
    }
    ```

* `/api/visual/user/submit?uid={uid}&username={username}`

    @GET，通过UID或USERNAME获取某个用户30天内提交统计，UID和USERNAME对应用户不同时优先UID

    ```json
    {
        "submit": [
            [
                long,int //分别对应x轴的时间（ms）和y轴的提交次数
            ],
        ],
        "ac": [
            [
                long,int //分别对应x轴的时间（ms）和y轴的AC次数
            ]
        ]
    }
    ```

* `/api/visual/user/allpass?uid={uid}&username={username}`

    @GET，通过UID或USERNAME获取某个用户做题统计，UID和USERNAME对应用户不同时优先UID

    ```json
    [
        long, //对应x轴的时间（ms），代表此处通过了一道未通过的题目
    ]
    ```

* `/api/visual/admin/register`

    @GET，获取最近30天内DOJ注册数据

    ```json
    [
        [
            long,int //分别对应x轴的时间（ms）和y轴的数量
        ]
    ]
    ```

* `/api/visual/admin/active`

    @GET，未完成

    ```json
    null
    ```
