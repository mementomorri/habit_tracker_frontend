package data


class Daily (
    override var name: String,
    override var description: String,
    override var difficulty: String,
    override val characterId: Int,
    override var completionCount: Int?= 0,
    override val startDate: Date,
    override var deadline: Date?
): Task(name,description,difficulty,"DAILY",characterId, deadline, startDate, completionCount) {

}