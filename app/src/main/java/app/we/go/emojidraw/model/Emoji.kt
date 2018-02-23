package app.we.go.emojidraw.model

data class Emoji(val id: Int,
                 val categoryId: Int,
                 val emoji: String,
                 val sinceVersion: Int,
                 val description: String)