package com.msa.userservice.exception

import java.sql.SQLException

class DuplicateEntityException(message: String): SQLException(message)