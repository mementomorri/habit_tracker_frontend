package data

class ToDo (
    override var name: String,
    override var description: String,
    override var difficulty: String,
    override val characterId: Int,
    override var deadline: Date?

): Task(name,description,difficulty,"TODO",characterId, deadline, null, null) {

}