package com.epam.command;

import com.epam.command.page.*;
import com.epam.command.page.BookDeliveryDeskCommand;
import com.epam.command.page.CreateUserCommand;
import com.epam.command.procedure.ShowImageCommand;

public enum CommandEnum {
    INDEX_PAGE(new IndexPageCommand()),
    ADMIN_PAGE(new AdminPageCommand()),
    LIBRARIAN_PAGE(new LibrarianPageCommand()),
    CURRENT_DELIVERY_DESK_PAGE(new CurrentDeliveryDeskCommand()),
    USER_PAGE(new UserPageCommand()),
    USER_ACCOUNT_PAGE(new UserAccountPageCommand()),
    REGISTRATION_PAGE(new RegistrationPageCommand()),
    USER_MANAGE_PAGE(new UserManagePageCommand()),
    BOOK_MANAGE_PAGE(new BookManagePageCommand()),
    ERROR_PAGE(new ErrorPageCommand()),
    SHOW_IMAGE(new ShowImageCommand()),
    BOOK_VS_DELIVERY_DESK(new BookDeliveryDeskCommand()),
    CREATE_USER(new CreateUserCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
