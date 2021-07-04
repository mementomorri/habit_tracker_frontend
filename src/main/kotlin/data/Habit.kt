package data

class Habit(
        override var name: String,
        override var description: String,
        override var difficulty: String= "MEDIUM",
        override val characterId: Int,
        override val startDate: Date?,
        override var completionCount: Int?= 0
): Task(name,description,difficulty,"HABIT",characterId, null, startDate, completionCount) {

}