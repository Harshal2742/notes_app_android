package com.example.composenotes.data


object DataSource {
    val Notes = listOf(
        Note(
            id = 1,
            date = "01/01/2023",
            title = "Note1",
            description = "India is one of the fastest growing country"
        ),
        Note(
            id = 2,
            date = "14/01/2023",
            title = "Phasellus eu pulvinar tortor",
            description = "Pellentesque fermentum  massa at velit maximus ultrices. Suspendisse euismod rhoncus sapien, sit amet pretium arcu mollis a. Cras commodo lacus erat"
        ),
        Note(
            id = 3,
            date = "01/04/2023",
            title = "Donec dictum est nec auctor condimentum",
            description = "Morbi nulla massa, accumsan ut congue tempus, aliquam sit amet libero. Mauris euismod, est nec consectetur blandit, diam nisi lobortis enim, vitae suscipit libero erat hendrerit sapien. Nam vel sapien ipsum. Quisque sagittis lectus lacinia orci aliquet"
        ),
        Note(
            id = 4,
            date = "04/05/2023",
            title = "Aliquam venenatis nunc justo, at dignissim arcu bibendum non",
            description = "India is one of the fastest growing country"
        ),
        Note(
            id = 5,
            date = "01/01/2023",
            title = "Proin sed scelerisque neque",
            description = "Sed nec dictum sem. Pellentesque dictum ante ac quam lobortis placerat. Suspendisse consectetur, sapien consectetur ornare venenatis, ante quam mollis lorem, a vehicula est enim sed quam. Etiam volutpat lacus diam, in elementum erat pharetra eget. Pellentesque"
        ),
        Note(
            id = 6,
            date = "12/01/2023",
            title = "Ut egestas consectetur massa, vitae vulputate diam feugiat vitae",
            description = "In at pharetra ligula, quis convallis nisl. Morbi enim ipsum, commodo non sem eget, pharetra porta nibh. Donec maximus, dui malesuada auctor suscipit, mauris felis bibendum dui, pellentesque tempor elit risus in odio. Fusce ante justo, pharetra non scelerisque id, interdum at enim."
        ),
        Note(
            id = 7,
            date = "14/01/2023",
            title = "Interdum et malesuada fames ac ante ipsum primis in faucibus",
            description = "Etiam nisl eros, elementum ac tempor in, porta a odio. Morbi rutrum lobortis eros. Praesent sagittis sollicitudin erat, ac egestas orci posuere non. "
        ),
        Note(
            id = 8,
            date = "02/03/2023",
            title = "Nulla in tellus augue",
            description = "Sed non fermentum erat, et venenatis ipsum. Aliquam eleifend imperdiet volutpat. Aliquam egestas ipsum mauris, et commodo dui consectetur quis. Vestibulum rhoncus nulla quis feugiat interdum. In imperdiet purus metus, id condimentum lectus ultricies vel. Curabitur ut interdum nisi. In sed nunc suscipit, luctus mauris eu, congue sapien. Suspendisse ornare quis odio eget suscipit. In accumsan urna nunc,"
        ),
        Note(
            id = 9,
            date = "01/02/2023",
            title = "Morbi sagittis sodales justo, ac auctor felis sagittis pulvinar",
            description = "Nulla orci mi, dapibus vel tempor vulputate, luctus at quam. Curabitur tempus sed mi eget pretium. Sed vitae eleifend nibh. Integer"
        )
    )

    val Todos = listOf(
        Todo(1, "Buy groceries", false),
        Todo(2, "Finish work presentation", false),
        Todo(3, "Go for a run", true),
        Todo(4, "Read a book", false),
        Todo(5, "Call a friend", true),
        Todo(6, "Organize desk", false),
        Todo(7, "Watch a movie", true),
        Todo(8, "Cook dinner", false),
        Todo(9, "Plan vacation", false),
        Todo(10, "Learn a new song on guitar", true)
    )
}