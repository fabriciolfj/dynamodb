resource "aws_dynamodb_table" "basic-dynamodb-table" {
  name           = "Product"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "id"
  range_key      = "categoria"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "dataCadastro"
    type = "S"
  }

  attribute {
    name = "categoria"
    type = "S"
  }

  global_secondary_index {
    name               = "dataCadastro-index"
    hash_key           = "categoria"
    range_key           = "dataCadastro"
    write_capacity     = 5
    read_capacity      = 10
    projection_type    = "ALL"
  }

  tags = {
    Name        = "dynamodb-table-1"
    Environment = "production"
  }
}

resource "aws_dynamodb_table" "basic-dynamodb-table2" {
  name           = "Recados"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "dataCadastro"
    type = "S"
  }

  attribute {
    name = "categoria"
    type = "S"
  }

  global_secondary_index {
    name               = "dataCadastro-rec-index"
    hash_key           = "categoria"
    range_key           = "dataCadastro"
    write_capacity     = 5
    read_capacity      = 10
    projection_type    = "ALL"
  }

  tags = {
    Name        = "dynamodb-table-2"
    Environment = "production"
  }
}