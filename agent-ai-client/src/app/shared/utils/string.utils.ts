export class StringUtils {

  public static isEmpty(value?: string | null): boolean {
    return value === null || value === undefined || value === '' || value === ' ' || value.length === 0;
  }
}
