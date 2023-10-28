# Unibell Test example

## Clients

### GET /api/clients/
Возвращает список всех клиентов.

### POST /api/clients/
Создание нового клиента. Тело запроса:

```json
{
    "first_name": "string",
    "last_name": "string"
}
```

### GET /api/clients/{id}/
Получение информации о клиенте по id, где `{id}` - идентификатор клиента.


## Contacts

### GET /api/clients/{clientId}/contacts/
Получение информации о контактах клиента, где `{clientId}` - идентификатор клиента.

Параметры запроса:

`typeContact` - тип запрашиваемых контактов. Допустимые значения `emails`, `phones`. 
*Для получения значений всех типов параметр не нужен.*

### POST /api/clients/{clientId}/contacts/
Добавление информации о контактах клиента, где `{clientId}` - идентификатор клиента. Тело запроса:

```json
{
  "phones": [
    "string"
  ],
  "emails": [
    "string"
  ]
}
```

## Response
Ответ возвращается в стандартной структуре

```json
{
    "status": "string",Статус
    "description": "string", Описание операции/ошибки
    "data":	{}
}
```

Где: 
- status - результат выполнения операции. Доступные значения: `ok`, `error`.
- description - описание операции/ошибки
- data - значащие данные.

**Более подробную документацию API можно посмотреть в Swagger**

`
http://localhost:8080/swagger-ui/index.html#/
`