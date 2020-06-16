export interface PageModel<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
}
