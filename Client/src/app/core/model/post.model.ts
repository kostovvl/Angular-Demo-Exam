export default interface Post{
    id: number,
    title: string,
    content: string
    approved: boolean,
    creatorName: string,
    creatorId: number,
    categoryId: number
}